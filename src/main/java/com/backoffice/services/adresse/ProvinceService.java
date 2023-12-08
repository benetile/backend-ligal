package com.backoffice.services.adresse;

import com.backoffice.entites.adresse.Province;
import com.backoffice.entites.adresse.Ville;
import com.backoffice.repositories.ProvinceRepository;
import com.backoffice.repositories.VilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProvinceService {

    private List<String> provinces = new ArrayList<>();

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private VilleRepository villeRepository;

    Logger logger = LoggerFactory.getLogger(ProvinceService.class);

   // @Scheduled(fixedDelay = 438000 )
    public void initProvinces(){
        provinces = Arrays.asList("Bas-Uele","Équateur","Haut-Katanga","Haut-Uèle", "Ituri","Kasaï","Kasaï Central","Kasaï Oriental", "Kinshasa",
                "Kongo-Central","Kwango","Kwilu","Lomami","Lualaba","Mai-Ndombe", "Manieme","Mongala","Nord-Kivu","Nord-Ubangi","Sankuru","Sud-Kivu",
                "Sud-Ubangi","Tanganyika","Tshopo","Tshuapa");

        logger.info("Début de vérification de provinces");
        for (String pr: provinces) {
            String pro = pr.replaceAll("-","");
            if(provinceRepository.findByNom(pro).isEmpty()){
                Province province = new Province();
                String nom = pro;//.replaceAll("-"," ");
                province.setNom(nom.toUpperCase());
                provinceRepository.save(province);
                logger.info("La province de "+pro+ " vient d'être ajouté dans la base des données le "+new Date( ));
            }
        }
    }

    public Province creationProvince(Province province){
        List<Province> provinces = provinceRepository.findAll();
        Boolean trouver = false;

        try {
            province.setNom(province.getNom().toUpperCase());
            logger.info("Vérification de la province ");
            if(provinceRepository.findByNom(province.getNom()).isEmpty()){
                provinceRepository.save(province);
                logger.info("Fin de la sauvegarde");
            }else{
                logger.warn("La province existe déjà ");
                return province;
            }

            return province;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Province modifierProvince(Province update, long id){
        /*List<Ville> villes = update.getVilles();
        Province province = provinceRepository.findById(update.getId()).orElseThrow(()-> new IllegalArgumentException(""));

        List<Ville> villesExist = new ArrayList<>();// province.getVilles();
        province.setVilles(villesExist);
        logger.info("Début de la mis à jour de la province "+update.getNom());
        logger.info("Nombre de villes "+update.getVilles().size());
        villes.parallelStream().forEach(ville -> {
            logger.info("L'ajout de la ville "+ville.getNom()+ " dans la province "+province.getNom());
            if(!villesExist.contains(ville)){
                ville.setProvince(province);
                villeRepository.save(ville);
                province.getVilles().add(ville);
                logger.info("La ville de "+ville.getNom()+ " a été attribuée à la province "+province.getNom());
                provinceRepository.save(province);
            }else{
                logger.info("Ville de "+ville.getNom()+ " appartient déjà à la province de "+province.getNom());
            }
        });
        Province saveProvince = provinceRepository.findById(update.getId()).orElseThrow(()-> new IllegalArgumentException(""));*/
        return null;
    }
}
