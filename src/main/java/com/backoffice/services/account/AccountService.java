package com.backoffice.services.account;

import com.backoffice.entites.*;
import com.backoffice.entites.Users.User;
import com.backoffice.entites.adresse.*;
import com.backoffice.repositories.*;
import com.backoffice.services.PhotoService;
import com.backoffice.services.TelephoneService;
import com.backoffice.services.adresse.AdresseService;
import com.backoffice.services.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private TelephoneService telephoneService;
    @Autowired
    private AdresseService adresseService;
    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Value("${ressource.directory.photo.actualite}")
    String actualiteDirectory;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Account creation(Account account){
        /** On verifie tout  d'abord si le numéro renseigné par le nouveau compte n'existe pas car le numero de téléphone doit être unique à un compte */
        Telephone tel = account.getTelephone();
        Adresse adresse =account.getAdresse();
        if (Objects.nonNull(account.getId())){
            Account oldAccount = accountRepository.findById(account.getId()).orElseThrow(()-> new IllegalArgumentException(""));
            User user = userRepository.findByAccountId(account.getId()).orElseThrow(()-> new IllegalArgumentException("Invalid Id : "+account.getId()));

            logger.info("Debut de la mise à jour de l'utilisateur portant l'id : "+account.getId());
            if(accountRepository.findByIdAndEmail(account.getId(),oldAccount.getEmail()).isPresent()){
                if(!oldAccount.getEmail().equalsIgnoreCase(account.getEmail())){
                    logger.info("L'utilisateur a modifier son Email "+oldAccount.getEmail()+" par : "+account.getEmail());
                    user.setEmail(account.getEmail());
                }else if (accountRepository.findByEmail(account.getEmail()).isEmpty()){
                    logger.info("L'utilisateur n'a pas modifier son adresse Email "+account.getEmail());
                }
            }

            if (Objects.nonNull(tel)){
                account.setTelephone(telephoneService.verifcationTelephone(tel));
            }
            if(Objects.nonNull(adresse)){
                account.setAdresse(adresseService.creationEtModificationAdresse(adresse));
            }

            Account accountSave = accountRepository.save(account);

            //userService.creationUser(accountSave);

            logger.info("Fin de la mise à jour des informations de l'utilsateur portant l'id : "+account.getId());
            return accountSave;

        }else{
            logger.info("Debut de la création d'un nouveau compte ");
            if (accountRepository.findByEmail(account.getEmail()).isEmpty()){
                logger.info("L'email " +account.getEmail()+" est valide ");
            }else {
                throw new IllegalArgumentException("Email "+account.getEmail()+" est déjà utilisé par un autre utilisateur ");
            }

            if (Objects.nonNull(tel)){
                account.setTelephone(telephoneService.verifcationTelephone(tel));
            }

            if (adresse != null) {
                account.setAdresse(adresseService.creationEtModificationAdresse(adresse));
            }

            Account accountSave = accountRepository.save(account);

           // userService.creationUser(accountSave);

            logger.info("L'ajout de l'utilisateur "+account.getNom()+ " " +account.getPrenom()+" dans la base des données ");
            logger.info("L'utilisateur a été enregistrer avec succès ");
            return accountSave;

        }
    }


    public void suppressionAccount(long id){
        Account account = accountRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id"));
        logger.info("suppression des annonces associées à l'utilisateur ");
        account.getAnnonces().forEach(annonce -> {
            logger.info("Debut des suppression des photos de l'annonce");
            String titre = annonce.getReference();
            annonce.getPhotos().forEach(photo -> {
                logger.info("Suppression de la photo : "+photo.getNom());
                photoRepository.delete(photo);
            });
            photoService.suppressionDuDossier(annonce.getReference());
            annonceRepository.delete(annonce);
            logger.info("Fin  de la suppression de l'annonce : " +titre);
        });
        if(Objects.nonNull(account.getAdresse())){
            account.setAdresse(new Adresse());
        }
        userService.supprimerUsers(account);
        accountRepository.deleteById(id);

        logger.info("Fin de la suppression de compte utilisateur ");
    }
}
