package com.backoffice.controllers.annonce;

import com.backoffice.entites.annonce.Annonce;
import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.repositories.AnnonceRepository;
import com.backoffice.services.annonce.AnnonceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/annonce")
@CrossOrigin
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private AnnonceRepository annonceRepository;

    Logger logger = LoggerFactory.getLogger(AnnonceController.class);

    @GetMapping("/")
    public List<Annonce> toutesLesAnnonces(){
        return annonceRepository.findAll();
    }
    @PostMapping("/creation")
    public Annonce creation(@RequestBody Annonce annonce){
        return annonceService.creation(annonce);
    }

    @GetMapping("/all")
    public List<Annonce> afficherlesAnnoncesParOrderDesc(){
        return annonceRepository.findAll(Sort.by(Sort.Order.desc("date")));
    }

    @GetMapping("/parAccount/{id}")
    public  List<Annonce> afficherLesAnnoncesParAccount(@PathVariable("id") Long id){
        return annonceRepository.findByAccountId(id);
    }


    @GetMapping("/{id}")
    public Optional<Annonce> rechercheParId(@PathVariable("id") long id){
        logger.info("Recherche de l'annonce grâce à son id : "+id);
        return annonceRepository.findById(id);
    }
    @GetMapping("/par/{id}")
    public Optional<Annonce> visualiserAnnonceParId(@PathVariable("id") long id){
        Annonce annonce =annonceRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(""));
        if (Objects.isNull(annonce.getVue())){
            annonce.setVue(1L);
        }else{
            annonce.setVue(annonce.getVue() + 1);
        }
        annonceRepository.save(annonce);
        logger.info("Recherche de l'annonce grâce à son id : "+id);
        return annonceRepository.findById(id);
    }

    @PostMapping("/filtre")
    public List<Annonce> filtreAnnonceParType(@RequestBody List<TypeAnnonce> typeAnnonces){
        try {
            return annonceService.filtreAnnonceParType(typeAnnonces);
        }catch (Exception ignored ){ }

        return annonceRepository.findAll();
    }

    @PostMapping("/filtre-annonce")
    public List<Annonce> filtreAvancerAnnonce(@RequestBody List<TypeAnnonce> typeAnnonces,
                                              @RequestParam("max") BigDecimal max, @RequestParam("min") BigDecimal min){
        logger.info("Max : "+max+ " et min : "+min);
        try {
            return annonceService.filtreParMontantEtType(typeAnnonces,min,max);
        }catch (Exception ignored){
        }
        return annonceRepository.findAll();
    }

    @GetMapping("/filtre-par-nom")
    public List<Annonce> rechercheParVilleEtParMotCle(@RequestParam("ville") String ville, @RequestParam("motCle") String motCle){
        return annonceService.rechercheParVilleEtParNomIgnoreCase(ville, motCle);
    }

    @GetMapping("/max")
    public BigDecimal afficherLePrixEleve(){
        System.out.println(annonceRepository.findFirstByOrderByMontantDesc());
        Annonce annonce = annonceRepository.findFirstByOrderByMontantDesc();
        return annonce.getMontant();
    }

    @DeleteMapping("/{id}")
    public void suppression(@PathVariable("id") long id){
        logger.info("Début de la suppression de l'annonce ");
        annonceService.suppressionAnnonce(id);
    }
}
