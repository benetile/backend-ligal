package com.backoffice.controllers.adresse;

import com.backoffice.entites.adresse.Commune;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.CommuneRepository;
import com.backoffice.repositories.VilleRepository;
import com.backoffice.services.adresse.CommuneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/commune")
@CrossOrigin
public class CommuneController {

    @Autowired
    private CommuneService communeService;
    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private VilleRepository villeRepository;

    Logger logger = LoggerFactory.getLogger(CommuneController.class);

    @GetMapping("/")
    public List<Commune> showAllCommune(){
        List<Commune> communes = communeRepository.findAll();

        return communes;
    }

    @GetMapping("/{id}")
    public Optional<Commune> getCommuneById(@PathVariable("id") long id){
        if(communeRepository.findById(id).isPresent()){
            return communeRepository.findById(id);
        }
        throw new IllegalArgumentException("Invalid id ");
    }

    @GetMapping("/nom/{nom}")
    public Optional<Commune> getCommuneByNom(@PathVariable("nom") String nom){
        if(communeRepository.findByNom(nom).isPresent()){
            return communeRepository.findByNom(nom);
        }
        throw new IllegalArgumentException("Invalid nom ");
    }

    @GetMapping("/recherche-commune/{id}/{nom}")
    public List<Commune> rechercheCommuneParVille(@PathVariable("id") long id, @PathVariable("nom") String nom){
        return communeRepository.findByVilleIdAndNomContainsIgnoreCase(id, nom);
    }

    @GetMapping("/search/{nom}")
    public List<Commune> rechercheParNom(@PathVariable("nom") String nom){
        if (!nom.isEmpty()){
            return communeRepository.findByNomContainsIgnoreCase(nom);
        }else{
            return communeRepository.findAll();
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Commune>> affichageParPageEtParNombre(@RequestParam(defaultValue = "0" )int page, @RequestParam int size){
        logger.info("Test de pagination "+page+ " size : "+size);
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Commune> communes = communeRepository.findAll(paging);

            if(communes!= null){
                return new ResponseEntity<>(communes, HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/ville/{id}")
    public List<Commune> getCommuneContainsVille(@PathVariable("id") long id){
        Ville ville = villeRepository.findById(id).get();
        logger.info("Recupération des communes de la ville : "+ville.getNom());
        return communeRepository.findByVilleId(id);
    }


    @PostMapping("/creation")
    public Commune createCommune(@RequestBody Commune commune){
        return communeService.creation(commune);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable("id") long id){
        if (communeRepository.findById(id).isPresent()){
            communeRepository.deleteById(id);
            logger.info("Fin de la suppréssion de la commune portant l'id "+id);
        }
    }
}
