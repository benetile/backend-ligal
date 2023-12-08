package com.backoffice.controllers.annonce;

import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.services.annonce.TypeAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/type")
@CrossOrigin
public class TypeAnnonceController {

    @Autowired
    private TypeAnnonceService typeAnnonceService;

    @GetMapping("/")
    public List<TypeAnnonce> showAllTypeAnnounce(){
        return typeAnnonceService.findAll();
    }

    @PostMapping("/create")
    public TypeAnnonce create(@RequestBody TypeAnnonce typeAnnonce){
        return typeAnnonceService.createTypeAnnounce(typeAnnonce);
    }

    @GetMapping("/{id}")
    public TypeAnnonce getTypeAnnounceById(@PathVariable("id") long id){
        if (typeAnnonceService.findById(id).isPresent()){
            return typeAnnonceService.findById(id).get();
        }else
            throw new IllegalArgumentException("Invalid Id "+id);
    }

    @GetMapping("/recherche-nom/{nom}")
    public Boolean rechercheTypeAnnonceParNom(@PathVariable("nom") String nom){
        return typeAnnonceService.findByNomIgnoreCase(nom).isPresent();
    }
}
