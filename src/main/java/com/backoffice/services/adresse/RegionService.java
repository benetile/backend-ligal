package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Region;
import com.backoffice.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    Logger logger = LoggerFactory.getLogger(RegionService.class);

    public Region creationRegion(Region region){
        if (regionRepository.findByNomIgnoreCase(region.getNom()).isEmpty()){
            logger.info("Sauvegarde de la region "+region.getNom());
            return regionRepository.save(region);
        }else{
            return regionRepository.findByNomIgnoreCase(region.getNom()).get();
        }
    }





}
