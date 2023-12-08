package com.backoffice.controllers;

import com.backoffice.entites.Corporation;
import com.backoffice.repositories.CorporationRepository;
import com.backoffice.services.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import java.util.List;

//@RestController
//@RequestMapping(path = "/corporation")
//@CrossOrigin
public class CorporationController {

 /**   @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private CorporationService corporationService;

    @GetMapping("/")
    public List<Corporation> getAllCorporations(){
        return corporationRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Corporation getCorporationById(@PathVariable("id") long id){
        return corporationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Id "+id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCorporation(@PathVariable("id") long id){
        if(corporationRepository.existsById(id)){
            corporationRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Invalid ID "+id);
        }
    }

    @PostMapping("/add")
    public Corporation saveCorporation(@RequestBody Corporation corporation){
       return corporationService.createCorporation(corporation);
    }

    @PutMapping("/update/{id}")
    public Corporation updateCorporation(@RequestBody Corporation corporation, @PathVariable("id") long id){
        return corporationService.updateCorporation(corporation, id);
    }*/
}
