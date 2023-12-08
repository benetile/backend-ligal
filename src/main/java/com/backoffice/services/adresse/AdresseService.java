package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Adresse;
import com.backoffice.entites.adresse.Commune;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.AdresseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdresseService {

    /*@Autowired
    private ProvinceService provinceService;*/

    @Autowired
    private CommuneService communeService;

    @Autowired
    VilleService villeService;

    @Autowired
    private AdresseRepository adresseRepository;

    Logger logger = LoggerFactory.getLogger(AdresseService.class);


    public Adresse creationEtModificationAdresse(Adresse adresse){
        if (Objects.nonNull(adresse.getVille())){
            Ville ville = adresse.getVille();
            if(adresseRepository.findByAdresseIgnoreCaseAndNumeroAndVilleCodePostal(adresse.getAdresse(), adresse.getNumero(), ville.getCodePostal()).isEmpty()){
                logger.info("Création d'une nouvelle adresse");
                logger.info("l'adresse saisie n'existe pas dans a base des données ");
                adresse.setAdresse(adresse.getAdresse().toUpperCase());
                Adresse saveAdresse = adresseRepository.save(adresse);
                adresse = saveAdresse;

            }else{
                logger.info("l'adresse existe dans la base des données ");
                adresse = adresseRepository.findByAdresseIgnoreCaseAndNumeroAndVilleCodePostal(adresse.getAdresse(), adresse.getNumero(), ville.getCodePostal()).orElseThrow(()-> new IllegalArgumentException("Invalid adresse"));
            }
        }

        return adresse;
    }

}
