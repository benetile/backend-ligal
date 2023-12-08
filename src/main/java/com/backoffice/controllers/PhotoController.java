package com.backoffice.controllers;

import com.backoffice.entites.Actualite;
import com.backoffice.entites.Photo;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.repositories.ActualiteRepositroy;
import com.backoffice.repositories.AnnonceRepository;
import com.backoffice.repositories.PhotoRepository;
import com.backoffice.repositories.TypeAnnounceRepository;
import com.backoffice.services.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/photo")
@CrossOrigin
public class PhotoController {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private TypeAnnounceRepository typeAnnounceRepository;

    @Autowired
    private ActualiteRepositroy actualiteRepositroy;

    Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @PostMapping("/creation/{id}/{nom}")
    public Photo sauvergardeDesPhotos(@RequestParam("file") MultipartFile file,@PathVariable("id") long idAnnonce,
                                      @PathVariable("nom") String nom) throws IOException {
        Annonce annonce = annonceRepository.findById(idAnnonce).orElseThrow(()-> new IllegalArgumentException("Id invalide : "+idAnnonce));
        return photoService.sauvergardePourAnnonce(annonce,file, nom);
    }
    @PostMapping("/actualite/{id}/{nom}")
    public Photo sauvergardeDesPhotosActualites(@RequestParam("file") MultipartFile file,@PathVariable("id") long idActualite,
                                      @PathVariable("nom") String nom) throws IOException {
        Actualite actualite = actualiteRepositroy.findById(idActualite).orElseThrow(()-> new IllegalArgumentException("Id invalide : "+idActualite));
        return photoService.sauvergardePourActualite(actualite,file, nom);
    }

    @PostMapping("/type-annonce/{id}/{nom}")
    public Photo sauvergardePhotoTypeAnnonce(@RequestParam("file") MultipartFile file,@PathVariable("id") long id,
                                                @PathVariable("nom") String nom) throws IOException {
        TypeAnnonce typeAnnonce = typeAnnounceRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Id invalide : "+id));
        return photoService.sauvegardePourTypeAnnonce(typeAnnonce,file, nom);
    }

    @GetMapping("/annonce-photo/{id}/{idPhoto}")
    public Photo actualiserPhotoannonce(@PathVariable("id") long id,@PathVariable("idPhoto") long idPhoto){
        Annonce annonce = annonceRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Id annonce : "+id));
        Photo photo = photoRepository.findById(idPhoto).orElseThrow(()-> new IllegalArgumentException("Invalid Id photo : "+idPhoto));
        photo.setAnnonce(annonce);
        photoRepository.save(photo);
        logger.info("fin de la mise à jour de la photo : "+photo.getNom());
        return photo;
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable("id") long id){
        logger.info("Debut de la suppression de la photo");
        photoRepository.deleteById(id);
        logger.info("Fin de la suppression");
    }
}
