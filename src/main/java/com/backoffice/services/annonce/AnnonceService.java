package com.backoffice.services.annonce;

import com.backoffice.entites.Account;
import com.backoffice.entites.adresse.Adresse;
import com.backoffice.entites.adresse.Departement;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.repositories.AccountRepository;
import com.backoffice.repositories.AnnonceRepository;
import com.backoffice.repositories.PhotoRepository;
import com.backoffice.repositories.TypeAnnounceRepository;
import com.backoffice.services.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private TypeAnnounceRepository typeAnnounceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    //threads
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    Logger logger = LoggerFactory.getLogger(AnnonceService.class);

    @Transactional
    public Annonce creation(Annonce annonce){
        if (Objects.nonNull(annonce.getTypeAnnonce().getId())){
            long typeId= annonce.getTypeAnnonce().getId();
            TypeAnnonce type = typeAnnounceRepository.findById(typeId).orElseThrow(()-> new IllegalArgumentException("Invalid Id : "+typeId));
            logger.info("Catégorie de l'annonce est : "+type.getNom());
            annonce.setTypeAnnonce(type);
        }else {
            logger.info("L'annonce ne possède pas de Type d'annonce ");
            throw new IllegalArgumentException("Type annonce not cannot");
        }
        logger.info("Début de la création d'une nouvelle annonce "+annonce.getTitre());
        annonce.setDate(new Date());
        if (Objects.isNull(annonce.getStatus())){
            annonce.setStatus("Non verifié");
        }
        Account account = annonce.getAccount();
        Adresse adresse = account.getAdresse();
        annonce.setVille(adresse.getVille().getNom());
        annonceRepository.save(annonce);
        annonce.setReference(genererUneReference(annonce.getId()));
        return  annonceRepository.save(annonce);
    }

    public String genererUneReference(long idAnnounce){
        long number = idAnnounce;//commandRepository.findAll().size() + 1;
        String prefix = "LIGAL-";
        int boucle = 8;
        int length = String.valueOf(number).length();
        for (int i = 0;i< boucle - length;i++){
            prefix +=0;
        }
        prefix +=number;
        return prefix;

    }
    //@Scheduled(fixedDelay = 438000 )
    public void actualiserAnnonce(){
        List<Annonce> annonces = annonceRepository.findAll();
        for (Annonce annonce: annonces) {
            Account account = annonce.getAccount();
            Adresse adresse = account.getAdresse();
            annonce.setVille(adresse.getVille().getNom());
            annonceRepository.save(annonce);
        }
    }

    public void suppressionAnnonce(long id){
        Annonce annonce = annonceRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Id"));
        String reference = annonce.getReference();
        annonce.getPhotos().forEach(photo -> {
            logger.info("Suppression de la photo : "+photo.getNom());
            photoRepository.delete(photo);
        });
        annonce.setAccount(new Account());
        annonceRepository.delete(annonce);
        photoService.suppressionDuDossier(reference);
        logger.info("Fin de la suppression de l'annonce "+annonce.getReference());
    }

    public List<Annonce> filtreAnnonceParType(List<TypeAnnonce> categories){
        List<Annonce> annoncesParType = new ArrayList<>();
        if (categories.isEmpty()){
            return annonceRepository.findAll();
        }else{
            for (TypeAnnonce categ:categories) {
                List<Annonce> annonces = new ArrayList<>();
                annonces = annonceRepository.findByTypeAnnonceId(categ.getId());
                annoncesParType.addAll(annonces);
                logger.info("Le nom " +categ.getNom()+ " taille "+annonces.size());
            }
            return annoncesParType;
        }
    }

    public List<Annonce> filtreParMontantEtType(List<TypeAnnonce> categories, BigDecimal min, BigDecimal max){
        List<Annonce> annonces;
        if (categories.isEmpty()){
            annonces = annonceRepository.findByMontantBetween(min, max);
        }else{
            annonces = new ArrayList<>();
            for (TypeAnnonce categ: categories) {
                List<Annonce> annoncesProvisoire = annonceRepository.findByTypeAnnonceIdAndMontantBetween(categ.getId(),min,max);
                annonces.addAll(annoncesProvisoire);
            }
        }
        return annonces;
    }

    public List<Annonce> rechercheParVilleEtParNomIgnoreCase(String ville, String mot){
        System.out.println("Mot : "+mot+ " Ville : "+ville);
        if (ville.isEmpty() && mot.isEmpty()){
            logger.info("Aucun parametre dans la recherche par ville et par mot clé");
            return annonceRepository.findAll();
        } else if (!ville.isEmpty() && !mot.isEmpty()) {
            logger.info("Recherche par ville et par mot clé");
            return annonceRepository.findByVilleAndTitreContains(ville,mot);
        } else if (!mot.isEmpty()) {
            logger.info("Recherche par mot clé");
            return annonceRepository.findByTitreContains(mot);
        }else {
            logger.info("Recherche par ville ");
            return annonceRepository.findByVilleIgnoreCase(ville);
        }
    }

    public List<Annonce> filtreAnnonceParEmplacement(Account account){
        List<Annonce> annonces = new ArrayList<>();
        return annonces;
    }


}
