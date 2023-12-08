package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Departement;
import com.backoffice.entites.adresse.Region;
import com.backoffice.repositories.DepartementRepository;
import com.backoffice.repositories.RegionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionRepository regionRepository;

    Logger logger = LoggerFactory.getLogger(DepartementService.class);

    public void intialiserLesDepartement(){
        LocalDateTime debut = LocalDateTime.now();
        logger.info("Début de l'initialisation de departements de France à "+debut);
        ObjectMapper mapper = new ObjectMapper();

        String json ="C:\\Users\\Etile\\OneDrive\\Documents\\Projets\\france\\region_departement.json";

        try {
            int i = 0;
            int j =0;
            JsonNode jsonNode = mapper.readTree(new File(json));

            Iterator<String> iterator = jsonNode.fieldNames();
            iterator.forEachRemaining( s -> {
                Region region = new Region();
                region.setNom(s);
                if (regionRepository.findByNomIgnoreCase(region.getNom()).isEmpty()){
                    regionRepository.save(region);
                    logger.info("Sauvegarde de la Region : "+region.getNom());
                }
            });

            List<Region> regions = regionRepository.findAll();
            for (Region region: regions) {
                JsonNode departement = jsonNode.get(region.getNom());
                logger.info("lecture des departement de la region : "+region.getNom());
                for (JsonNode regionNode : departement) {
                    Departement dept = new Departement();
                    dept.setNom(regionNode.get("name").asText());
                    dept.setCode(regionNode.get("code").asText());
                    dept.setRegion(region);
                    if(departementRepository.findByNomAndCode(dept.getNom(),dept.getCode()).isEmpty()){
                        departementRepository.save(dept);
                        logger.info("fin de la création du département "+dept.getNom()+ " code "+dept.getCode());
                    }
                    i++;
                }
            }
            logger.info(" Fin du traitement de "+regions.size()+" régions et "+i+" départements");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LocalDateTime fin= LocalDateTime.now();
        int duree = fin.compareTo(debut);
        logger.info("Fin de traitement de la mise à jour des departement de France à "+fin);
        logger.info("Durée de traitement des données est de "+duree);
    }



}
