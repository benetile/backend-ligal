package com.backoffice.services.users;

import com.backoffice.entites.Account;
import com.backoffice.entites.Email;
import com.backoffice.entites.Telephone;
import com.backoffice.entites.Users.ERole;
import com.backoffice.entites.Users.User;
import com.backoffice.entites.adresse.Adresse;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.*;
import com.backoffice.services.EmailService;
import com.backoffice.services.PhotoService;
import com.backoffice.services.TelephoneService;
import com.backoffice.services.adresse.AdresseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TelephoneService telephoneService;
    @Autowired
    private AdresseService adresseService;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    //@Scheduled(fixedDelay = 8640000)
    public void initialisationAdminAccount(){
        Account account =new Account();
        account.setNom("Etilefanela wael");
        account.setPrenom("Benny");
        account.setEmail("etilebenny@gmail.com");
        if (accountRepository.findByEmail(account.getEmail()).isEmpty()){
            Account accountSave= accountRepository.save(account);
            if (userRepository.findByEmail(account.getEmail()).isEmpty()){
                User user = new User();
                ERole eRole = new ERole();
                user.setEmail(account.getEmail());
                user.setUsername(account.getPrenom()+"-"+account.getNom());
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode("ligal2023"));
                user.setAccount(accountSave);
                eRole = roleRepository.findByNom("ROLE_ADMIN").orElseThrow(()-> new IllegalArgumentException("Invalid"));
                user.setERole(eRole);
                userRepository.save(user);
                account.setIdUser(user.getId());
                //creation de l'adresse

                Adresse adresse = new Adresse();
                adresse.setAdresse("Boulevard Gambetta");
                adresse.setNumero("51");
                Ville ville = villeRepository.findByCodePostal("95110").orElseThrow(()-> new IllegalArgumentException("Invalid Cp"));
                adresse.setVille(ville);

                adresse = adresseService.creationEtModificationAdresse(adresse);
                account.setAdresse(adresse);
                accountRepository.save(account);
                System.out.println("fin de la sauvergade de l'utilsateur "+user.getUsername());
            }
        }

    }

    public User creationUserEtAccount(User user){
        Account account = user.getAccount();
        Telephone tel = account.getTelephone();
        Adresse adresse =account.getAdresse();
        Email email = new Email();
        if (account.getId() != null){
            Account oldAccount = accountRepository.findById(account.getId()).orElseThrow(()-> new IllegalArgumentException(""));
            User oldUser = userRepository.findByAccountId(account.getId()).orElseThrow(()-> new IllegalArgumentException("Invalid Id : "+account.getId()));

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

            if (!oldUser.getUsername().equals(user.getUsername())) {
                oldUser.setUsername(user.getUsername());
            }

            Account accountSave = accountRepository.save(account);
            logger.info("Fin de la mise à jour des informations de l'utilsateur portant l'id : "+accountSave.getId());

            oldUser.setAccount(accountSave);
            User saveUser = userRepository.save(oldUser);
            logger.info("Fin de la mis à jour de compte utilisateur "+saveUser.getUsername());
            return saveUser;
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
            logger.info("Fin de la sauvegarde du compte portant l'id : "+accountSave.getId());
            user.setAccount(accountSave);
            User saveUser = creationUser(user);

            logger.info("L'ajout de l'utilisateur "+account.getNom()+ " " +account.getPrenom()+" dans la base des données ");
            logger.info("L'utilisateur a été enregistrer avec succès ");
            return saveUser;
        }
    }

    public User creationUser(User user){
        Account account = user.getAccount();
        String password = "";
        Email email = new Email();

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userRepository.findByEmail(account.getEmail()).isEmpty()){
            ERole eRole = new ERole();
            user.setEmail(account.getEmail());
            if (user.getUsername() == null){
                Random random = new Random();
                int randomNumber = random.nextInt(100) + 1;
                String nom = generationUsername(account.getNom(),account.getPrenom());
                String username =nom+"@"+randomNumber;
                user.setUsername(username);
            }
            if(user.getPassword() == null){
                user.setPassword(generationMotDePasse());
            }

            password = user.getPassword();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setAccount(account);
            eRole = roleRepository.findByNom("ROLE_CLIENT").orElseThrow(()-> new IllegalArgumentException("Invalid"));
            user.setERole(eRole);
            userRepository.save(user);

            account.setIdUser(user.getId());
            accountRepository.save(account);
            logger.info("Fin de la sauvergade du compte utilisateur ");

            email.setTitre("Création de compte");
            email.setDate(new Date());
            email.setDestinataire(user.getEmail());
            email.setCode(password);
            email.setEnvoye(false);
            Email saveEmail =emailRepository.save(email);
            emailService.confirmationCreationCompte(saveEmail);
            return user;
        }
        return null;
    }

    public String generationMotDePasse(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Ensemble de caractères parmi lesquels choisir
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public String generationUsername(String nom, String prenom){
        String character =nom+prenom;
        String username;
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(character.length());
            char randomChar = character.charAt(randomIndex);
            randomString.append(randomChar);
        }
        username = randomString.toString();
        logger.info("Fin de génération du username : -> "+username);
        return username;
    }
    public void supprimerUsers(Account account){
        User user = userRepository.findByAccountId(account.getId()).orElseThrow(()-> new IllegalArgumentException("Invalid ID : "));
        logger.info("username  "+user.getUsername());

        //user.setAccount(new Account());
        userRepository.delete(user);
        logger.info("Fin de la suppression de l'utilisateur "+user.getUsername());
    }
}
