package com.backoffice.controllers.adresse;

import com.backoffice.entites.adresse.Province;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.ProvinceRepository;
import com.backoffice.services.adresse.ProvinceService;
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
@RequestMapping(path = "/province")
@CrossOrigin
public class ProvinceController {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceService provinceService;

    Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @GetMapping("/")
    public List<Province> showAllProvince(){
        return provinceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Province> getProvinceById(@PathVariable("id") long id){
        if(provinceRepository.findById(id).isPresent()){
            return provinceRepository.findById(id);
        }
        throw new IllegalArgumentException("id not valid");
    }

    @PostMapping("/creation")
    public Province createProvince(@RequestBody Province province) throws Exception {
        return provinceService.creationProvince(province);

    }

    @GetMapping("/search/{nom}")
    public List<Province> rechercheProvinceParNom(@PathVariable("nom") String nom){
        List<Province> provinces = new ArrayList<>();
        if (!nom.isEmpty()){
            provinces = provinceRepository.findByNomContainsIgnoreCase(nom);
        }
        return provinces;
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Province>> affichageParPageEtParNombre(@RequestParam(defaultValue = "0" )int page, @RequestParam int size){
        logger.info("Test de pagination "+page+ " size : "+size);
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Province> provinces = provinceRepository.findAll(paging);

            if(provinces!= null){
                return new ResponseEntity<>(provinces, HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update/{id}")
    public Province updateProvince(@RequestBody Province province, @PathVariable("id") long id){
        logger.info("La province de "+province.getNom());
        if(provinceRepository.findById(province.getId()).isPresent()){
            return provinceService.modifierProvince(province, id);
        }else{
            return province;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void supprimerProvince(@PathVariable long id){
        if (provinceRepository.findById(id).isPresent()){
            provinceRepository.deleteById(id);
            logger.info("Fin de la suppréssion de la province portant l'Id "+id);
        }
    }


}
