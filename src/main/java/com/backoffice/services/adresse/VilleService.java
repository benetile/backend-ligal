package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Departement;
import com.backoffice.entites.adresse.Province;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.DepartementRepository;
import com.backoffice.repositories.ProvinceRepository;
import com.backoffice.repositories.RegionRepository;
import com.backoffice.repositories.VilleRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class VilleService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private RegionRepository regionRepository;

    public List<String> anomalie = new ArrayList<>();

    ExecutorService executorService = Executors.newFixedThreadPool(50);
    ExecutorService executorServiceParse = Executors.newFixedThreadPool(20);
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;


    Logger logger = LoggerFactory.getLogger(VilleService.class);

    /** cette méthode permet d'ajouter une nouvelle ville et l'attribué à la place qui correspond */
    public Ville creationDuneVille(Ville ville){
        long deptId = 0;
        Departement departement = departementRepository.findById(deptId).orElseThrow(()-> new IllegalArgumentException("l'Id est introuvable "));
        List<Ville> villes = new ArrayList<>();
        try {
            logger.info("Vérification de la ville avant la création "+departement.getId());
            if (villeRepository.findById(ville.getId()).isEmpty()){
                if(villeRepository.findByNomIgnoreCaseAndDepartementId(ville.getNom(), departement.getId()).isEmpty()){
                    logger.info("Début de la création d'une nouvelle ville");
                    ville.setNom(ville.getNom().toUpperCase());
                    Ville saveVille = villeRepository.save(ville);
                   if(!departement.getVilles().isEmpty()){
                        departement.getVilles().add(saveVille);
                        departementRepository.save(departement);
                    }else{
                        villes.add(ville);
                        departementRepository.save(departement);
                    }
                }else{
                    logger.warn("La ville renseignée existe déjà dans la base");
                }
            }else{
                logger.info("Début de la modification des informations de la ville");

                Ville ancienneVille = villeRepository.findById(ville.getId()).orElseThrow(()-> new IllegalArgumentException(""));
                Departement departementExist = ancienneVille.getDepartement();

                logger.info("On recupère les anciennes information de la ville avant de modifier");
                if(Objects.isNull(departementExist)){
                    if (villeRepository.findByNomIgnoreCaseAndDepartementId(ville.getNom(),departementExist.getId()).isEmpty()){
                        villeRepository.save(ville);
                        logger.info("Fin de la sauvegarde");
                    }
                }
                else if (departement.getId() == departementExist.getId()){
                    if (villeRepository.findByNomIgnoreCaseAndDepartementId(ville.getNom(),departement.getId()).isEmpty()){
                        villeRepository.save(ville);
                        logger.info("Fin de la sauvegarde");
                    }else{
                        logger.warn("La ville saisie existe déjà");
                        return ville;
                    }
                }else if (departement.getId() != ancienneVille.getId()){
                    if(villeRepository.findByNomIgnoreCaseAndDepartementId(ville.getNom(),departement.getId()).isEmpty()){
                        departementExist.getVilles().remove(ville);
                        villeRepository.save(ville);
                        departementRepository.save(departementExist);
                        logger.info("Fin de la modification");
                    }
                }
            }
            return ville;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //@Scheduled(fixedDelay = 846000)
    public void asynchroCreation(String [] strings){
        Runnable runnableTask = ()->{
            try {
               // creation(strings);
                //traitementcoorodnnees(villeJson);
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        executorServiceParse.submit(runnableTask);
    }

    //@Transactional
    public void initialisationDesVilles() {
        LocalDateTime debut = LocalDateTime.now();
        logger.info("Début de l'initialisation des villes de France le " + debut.toLocalDate() + " à " + debut.toLocalTime());
        ObjectMapper mapper = new ObjectMapper();
        String json = "C:\\Users\\Etile\\OneDrive\\Documents\\Projets\\france\\france.json";
        try {
            int i= 0;
            JsonNode jsonNode = mapper.readTree(new File(json));
            logger.info("verification de coordonnées gps");
            for (JsonNode villeJson : jsonNode) {
                traitementDesdonnees(villeJson);
                i++;
            }
            LocalDateTime fin = LocalDateTime.now();
            int duree = fin.getMinute() - debut.getMinute();
            logger.info("début du traitement à "+debut.toLocalTime()+ " et "+fin);
            logger.info("Fin de traitement des villes en "+duree+ " minutes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void traitementDesdonnees(JsonNode villeJson){
        Ville ville = new Ville();
        ville.setNom(villeJson.get("Nom_commune").asText());
        ville.setInsee(villeJson.get("Code_commune_INSEE").asText());
        ville.setCodePostal(villeJson.get("Code_postal").asText());
        ville.setLibelle(villeJson.get("Libelle_acheminement").asText());

        if(!villeJson.get("coordonnees_gps").asText().isEmpty()){
            String[] coordonnees_gps = villeJson.get("coordonnees_gps").asText().split(",");
            double lon = Double.parseDouble(coordonnees_gps[1]);
            double la = Double.parseDouble(coordonnees_gps[0]);
            ville.setLongitude(lon);
            ville.setLatitude(la);
        }

        String nom = villeJson.get("Nom_commune").asText();
        String cp = villeJson.get("Code_postal").asText();

        if (villeRepository.findByNomIgnoreCaseAndCodePostal(nom,cp).isEmpty()){
            if(!villeJson.get("coordonnees_gps").asText().isEmpty()){
                creation(ville);
            }
        }
    }

    public Ville creation(Ville ville){
        String code = null;
        if(ville.getCodePostal().startsWith("97")){
            code = ville.getCodePostal().substring(0,3);
        } else if (ville.getCodePostal().startsWith("20")) {
            code = ville.getCodePostal().substring(0,3);
            int codeRef = Integer.parseInt(code);
            if (codeRef < 202 ){
                code = "2A";
            } else{
                code = "2B";
            }
        } else if (ville.getNom().equalsIgnoreCase("MONACO")) {
            code="06";
        } else{
            code = ville.getCodePostal().substring(0,2);
        }
        String finalCode = code;
        Departement departement = new Departement();
        if (departementRepository.findByCodeStartsWith(code).isPresent()){
            departement = departementRepository.findByCodeStartsWith(code).orElseThrow(()-> new IllegalArgumentException("Invalid Id "+ finalCode));
            ville.setDepartement(departement);
            if (villeRepository.findByNomIgnoreCaseAndCodePostal(ville.getNom(), ville.getCodePostal()).isEmpty()){
                villeRepository.save(ville);
            }
        }else{
            logger.info("la ville portant le code "+ville.getCodePostal()+ " n'aura pas departement ");
            if(anomalie.isEmpty()){
                List<String> data = new ArrayList<>();
                data.add(finalCode);
                anomalie = data;
            }else{
                if (!anomalie.contains(finalCode)){
                    anomalie.add(finalCode);
                }
            }
        }
        return ville;
    }

    public Double calculDistance(String villeActuel){

        Ville ville = new Ville();
        double longitudeActuel = 0;
        double latitudeActuel = 0;
        double longitude =  Math.toRadians(ville.getLongitude());
        double latitude = Math.toRadians(ville.getLatitude());

        double angle = Math.acos(Math.sin(latitudeActuel) * Math.sin(latitude))
                + Math.cos(latitudeActuel) * Math.cos(latitude) * Math.cos(longitudeActuel - longitude);

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }
    public void asynchro(JsonNode villeJson){
        Runnable runnableTask = ()->{
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        executorService.submit(runnableTask);
    }

    public void asynchroFinaleExecutor() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(250, TimeUnit.SECONDS);
    }


}
