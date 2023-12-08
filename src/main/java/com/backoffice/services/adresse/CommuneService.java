package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Commune;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.CommuneRepository;
import com.backoffice.repositories.VilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommuneService {

    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private VilleRepository villeRepository;

    Logger logger = LoggerFactory.getLogger(CommuneService.class);

    public Commune creation(Commune commune){
        long idVille = commune.getVille().getId();
        Ville ville = villeRepository.findById(idVille).orElseThrow(()-> new IllegalArgumentException("Illegal id "+idVille));
        if(communeRepository.findByNomIgnoreCaseAndVilleId(commune.getNom(),ville.getId()).isEmpty()){
            commune.setVille(ville);
            commune.setNom(commune.getNom().toUpperCase());
            logger.info("Début de la sauvegarde de la commune "+commune.getNom()+ " dans la ville de "+ville.getNom());
            communeRepository.save(commune);
            return commune;
        }else{
            logger.warn("La commune existe déjà");
            return communeRepository.findByNomIgnoreCaseAndVilleId(commune.getNom(), ville.getId()).get();
        }
    }
}
