package com.backoffice.controllers.adresse;

import com.backoffice.entites.adresse.Province;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.ProvinceRepository;
import com.backoffice.repositories.VilleRepository;
import com.backoffice.services.adresse.VilleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ville")
@CrossOrigin
public class VilleController {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private VilleService villeService;
    /*@Autowired
    private ProvinceRepository provinceRepository;*/
    Logger logger = LoggerFactory.getLogger(VilleController.class);

    @GetMapping("/")
    public List<Ville> showAllVille(){
        return villeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Ville> getVilleById(@PathVariable("id") long id){
        logger.info("Recherche la ville portant l'id "+id);
        if (villeRepository.findById(id).isPresent()){
            return villeRepository.findById(id);
        }
        throw new IllegalArgumentException("invalid Id ");
    }

    @PostMapping("/creation")
    public Ville createVille(@RequestBody Ville ville) throws Exception {

        return villeService.creationDuneVille(ville);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Ville>> affichageParPageEtParNombre(@RequestParam(defaultValue = "0" )int page, @RequestParam int size){
        logger.info("Test de pagination "+page+ " size : "+size);
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Ville> villes = villeRepository.findAll(paging);

            if(villes!= null){
                return new ResponseEntity<>(villes,HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/nom/{nom}")
    public List<Ville> rechercheVilleParProvince( @PathVariable("nom") String nom){
        logger.info("Recherche de ville qui commence par "+nom);
        return villeRepository.findByNomStartsWith(nom);
    }
    @GetMapping("/code-postal/{cp}")
    public List<Ville> rechercheVilleParCodePostal(@PathVariable("cp") String cp){
        logger.info("Recherche de ville qui ayant "+cp+" comme code postal");
        return villeRepository.findByCodePostalStartsWith(cp);
    }

    @GetMapping("/search/{nom}")
    public List<Ville> rechercheVilleParNom(@PathVariable("nom") String nom){
        logger.info("Recherche des villes portant le nom : "+nom);
        List<Ville> villes = new ArrayList<>();
        if(!nom.isEmpty()){
            villes = villeRepository.findByNomContainsIgnoreCase(nom);
        }
        return villes;
    }

    @DeleteMapping("/delete/{id}")
    public void supprimerUneVille(@PathVariable("id") long id){
        logger.info("Suppression de la ville portant l'id : "+id);
        if(villeRepository.findById(id).isPresent()){
            villeRepository.deleteById(id);
            logger.info("Fin de la suppression de la ville portant l'id "+id);
        }else{
            throw new IllegalArgumentException("L'id est introuvable ");
        }
    }


}
