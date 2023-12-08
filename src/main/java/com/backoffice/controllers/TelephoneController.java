package com.backoffice.controllers;

import com.backoffice.entites.Telephone;
import com.backoffice.repositories.TelephoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/telephone")
@CrossOrigin
public class TelephoneController {

    @Autowired
    private TelephoneRepository telephoneRepository;

    Logger logger = LoggerFactory.getLogger(TelephoneController.class);

    @GetMapping("/")
    public List<Telephone> getAllTelephones(){
        return telephoneRepository.findAll();
    }

    @GetMapping("/numero/{numero}")
    public Boolean verifierSiNumeroExiste(@PathVariable("numero") String numero){
        logger.info("Vérification du numéro de téléphone "+numero);
        return telephoneRepository.findByNumero(numero).isPresent();
    }

    @GetMapping("/search/{numero}")
    public List<Telephone> afficherTousLesNumeros(@PathVariable("numero") String numero){
        return telephoneRepository.findByNumeroContains(numero);
    }
    
}
