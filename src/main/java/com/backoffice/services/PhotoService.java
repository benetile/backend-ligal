package com.backoffice.services;

import com.backoffice.entites.Actualite;
import com.backoffice.entites.Photo;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.repositories.ActualiteRepositroy;
import com.backoffice.repositories.PhotoRepository;
import com.backoffice.repositories.TypeAnnounceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ActualiteRepositroy actualiteRepositroy;

    @Autowired
    private TypeAnnounceRepository typeAnnounceRepository;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${ressource.directory.photo.annonce}")
    String annonceDirectory;

    @Value("${ressource.directory.photo.actualite}")
    String actualiteDirectory;

    @Value("${ressource.directory.photo.type}")
    String typeAnnonceDirectory;
    @Value("${ressource.directory.separateur}")
    String separateur;

    String prefixeFile ="file///";
    @Transactional
    public Photo sauvergardePourAnnonce(Annonce annonce, MultipartFile file, String nom){
        Photo photo = new Photo();
        String repertoire = annonceDirectory +annonce.getReference();
        logger.info("Photo "+nom);
        String reference = annonce.getReference()+"-"+nom ;
        String finalNom = reference.replaceAll("_","");
        photo.setNom(finalNom);
        photo.setExtension(file.getContentType());
        try {
            logger.info("Début de traitement de la photo pour l'annonce "+annonce.getReference());
           // photo.setFile(file.getBytes());
            photo.setDateDeCreation(new Date());
            photo.setAnnonce(annonce);
            logger.info("Début de la création de l'url de la photo");
            if (!file.isEmpty()){
                File fichier= new File(repertoire);
                if (!fichier.exists()){
                    fichier.mkdirs();
                }
                Path chemin = Path.of(repertoire,photo.getNom());
                Files.copy(file.getInputStream(),chemin,StandardCopyOption.REPLACE_EXISTING);
                String local = repertoire+separateur+photo.getNom();
                photo.setRepertoire(local);
                String url ="/assets/annonces/"+annonce.getReference()+separateur+photo.getNom();
                photo.setUrl(url);
                photoRepository.save(photo);
            }

            logger.info("Fin du traitement de la photo");
            return photo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Photo sauvergardePourActualite(Actualite actualite, MultipartFile file, String nom){
        Photo photo = new Photo();
        String repertoire = actualiteDirectory;
        logger.info("Photo "+nom);

        String finalNom = nom.replaceAll("_","").replaceAll(" ","-");
        photo.setNom(finalNom);
        photo.setExtension(file.getContentType());
        try {
            logger.info("Début de traitement de la photo pour l'actualité "+actualite.getId());
            photo.setDateDeCreation(new Date());
            photo.setActualite(actualite);
            logger.info("Début de la création de l'url de la photo");
            if (!file.isEmpty()){
                File fichier= new File(repertoire);
                if (!fichier.exists()){
                    fichier.mkdirs();
                }
                Path chemin = Path.of(repertoire,photo.getNom());
                Files.copy(file.getInputStream(),chemin,StandardCopyOption.REPLACE_EXISTING);
                String local = repertoire+separateur+photo.getNom();
                photo.setRepertoire(local);
                String url ="/assets/actualites/"+photo.getNom();
                photo.setUrl(url);
                photoRepository.save(photo);
                actualite.setPhoto(photo);
                actualiteRepositroy.save(actualite);
            }

            logger.info("Fin du traitement de la photo");
            return photo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Photo sauvegardePourTypeAnnonce(TypeAnnonce typeAnnonce,MultipartFile file, String nom){
        Photo photo = new Photo();
        String repertoire = typeAnnonceDirectory;
        logger.info("Chargement photo "+nom);

        String finalNom = nom.replaceAll("_","").replaceAll(" ","-");
        photo.setNom(finalNom);
        photo.setExtension(file.getContentType());
        try {
            logger.info("début de traitement de la photo du type d'annonce "+typeAnnonce.getNom());
            photo.setDateDeCreation(new Date());
            photo.setTypeAnnonce(typeAnnonce);
            logger.info("Début de la création de l'url de la photo");
            if (!file.isEmpty()){
                File fichier= new File(repertoire);
                if (!fichier.exists()){
                    fichier.mkdirs();
                }
                Path chemin = Path.of(repertoire,photo.getNom());
                Files.copy(file.getInputStream(),chemin,StandardCopyOption.REPLACE_EXISTING);
                String local = repertoire+separateur+photo.getNom();
                photo.setRepertoire(local);
                String url ="/assets/type-annonce/"+photo.getNom();
                photo.setUrl(url);
                photoRepository.save(photo);
                typeAnnonce.setPhoto(photo);
                typeAnnounceRepository.save(typeAnnonce);
            }
            logger.info("Fin du traitement de la photo");
            return photo;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public void suppressionDuDossier(String reference){
        String repertoire = annonceDirectory+reference;
        File folder = new File(repertoire);

        if (!folder.exists()) {
            return; // Folder doesn't exist
        }

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively delete subdirectories
                        suppressionDuDossier(file.getAbsolutePath());
                    } else {
                        // Delete files in the directory
                        file.delete();
                    }
                }
            }
        }
        // Delete the empty folder itself
        folder.delete();
    }
    public void creationDuRepertoire(String root){
        File path = new File(root);
        if (!path.exists()){
            path.mkdir();
            logger.info("Création du répertoire imgae : "+root);
        }
    }

}
