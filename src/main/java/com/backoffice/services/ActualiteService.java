package com.backoffice.services;

import com.backoffice.entites.Actualite;
import com.backoffice.repositories.ActualiteRepositroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActualiteService {

    @Autowired
    private ActualiteRepositroy actualiteRepositroy;

    Logger logger = LoggerFactory.getLogger(ActualiteService.class);

    public Actualite creation(Actualite actualite){
        if(actualiteRepositroy.findByTitreIgnoreCase(actualite.titre).isEmpty()){
            actualite.setDatePublication(new Date());
            actualiteRepositroy.save(actualite);
            logger.info("Fin de la sauvergarde de l'actualité");
        }
        return actualite;
    }
}
