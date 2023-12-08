package com.backoffice.controllers;

import com.backoffice.entites.Actualite;
import com.backoffice.repositories.ActualiteRepositroy;
import com.backoffice.services.ActualiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/actualite")
@CrossOrigin
public class ActualiteController {

    @Autowired
    private ActualiteRepositroy actualiteRepositroy;
    @Autowired
    private ActualiteService actualiteService;

    @PostMapping("/creation")
    public Actualite creationActualite(@RequestBody Actualite actualite){
        System.out.println("Création de titre : "+actualite.titre+ " - "+actualite.description);
        return actualiteService.creation(actualite);
    }


    @GetMapping("/")
    public List<Actualite> afficherToutesLesActualites(){
        return actualiteRepositroy.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Actualite> rechercheParId(@PathVariable("id") long id){
        return actualiteRepositroy.findById(id);
    }
}
