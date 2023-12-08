package com.backoffice.services.annonce;

import com.backoffice.entites.annonce.TypeAnnonce;
import com.backoffice.repositories.TypeAnnounceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class TypeAnnonceService implements TypeAnnounceRepository {

    @Autowired
    private TypeAnnounceRepository typeAnnounceRepository;

    Logger logger = LoggerFactory.getLogger(TypeAnnonceService.class);

    List<String> typeAnnonceList= Arrays.asList("Ameublement","Location Immobilier","Achat Immobilier","Location Vehicule"," Vehicule","Vetement","Eléctronique","Eléctroménager");

    //@Scheduled(fixedDelay = 483600)
    public void initTypeAnnounce(){
        logger.info("****** Initialisation de type d'annonce ******");
        for (String s: typeAnnonceList) {
            if (typeAnnounceRepository.findByNom(s).isEmpty()){
                TypeAnnonce typeAnnonce = new TypeAnnonce();
                typeAnnonce.setDateDeCreation(new Date());
                typeAnnonce.setNom(s.toUpperCase());
                typeAnnounceRepository.save(typeAnnonce);
                logger.info("Fin de la sauvegarde du type d'annonce : "+typeAnnonce.getNom());
            }
        }

    }

    public TypeAnnonce createTypeAnnounce(TypeAnnonce typeAnnonce){
        Date now = new Date();
        try {
            typeAnnonce.setNom(typeAnnonce.getNom().toUpperCase());
            //creation
            if (typeAnnonce.getId() == null){
            //if (typeAnnounceRepository.findById(typeAnnonce.getId()).isEmpty()) {
                logger.info("Debut de la création de nouveau type d'annonce ");
                typeAnnonce.setDateDeCreation(now);
                typeAnnonce.setDateModifier(now);
                return typeAnnounceRepository.save(typeAnnonce);
            } else if (typeAnnounceRepository.findById(typeAnnonce.getId()).isPresent()) {
                if (typeAnnounceRepository.findByNomIgnoreCase(typeAnnonce.getNom()).isEmpty()){
                    typeAnnonce.setDateModifier(now);
                    logger.info("Debut de la mise à jour du type d'annonce "+ typeAnnonce.getNom());
                    return typeAnnounceRepository.save(typeAnnonce);
                }
            }else{
                logger.warn("error mauvaise requête données introuvables");
            }
            logger.info("Fin du traitement ");
            return typeAnnonce;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<TypeAnnonce> findByNom(String nom) {
        return typeAnnounceRepository.findByNom(nom);
    }

    @Override
    public Optional<TypeAnnonce> findByIdAndNom(long id, String nom) {
        return typeAnnounceRepository.findByIdAndNom(id, nom);
    }

    @Override
    public Optional<TypeAnnonce> findByNomIgnoreCase(String nom) {
        return typeAnnounceRepository.findByNomIgnoreCase(nom);
    }

    @Override
    public List<TypeAnnonce> findAll() {
        return typeAnnounceRepository.findAll();
    }

    @Override
    public List<TypeAnnonce> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TypeAnnonce> findAll(Pageable pageable) {
        return typeAnnounceRepository.findAll(Pageable.unpaged());
    }

    @Override
    public List<TypeAnnonce> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return typeAnnounceRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        typeAnnounceRepository.deleteById(aLong);
        logger.info("Fin de la suppression du type portant l'id "+aLong);
    }

    @Override
    public void delete(TypeAnnonce entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TypeAnnonce> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TypeAnnonce> S save(S entity) {
        return typeAnnounceRepository.save(entity);
    }

    @Override
    public <S extends TypeAnnonce> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TypeAnnonce> findById(Long aLong) {
        return typeAnnounceRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TypeAnnonce> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TypeAnnonce> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TypeAnnonce> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TypeAnnonce getOne(Long aLong) {
        return null;
    }

    @Override
    public TypeAnnonce getById(Long aLong) {
        return null;
    }

    @Override
    public TypeAnnonce getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends TypeAnnonce> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TypeAnnonce> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TypeAnnonce> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TypeAnnonce> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TypeAnnonce> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TypeAnnonce> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TypeAnnonce, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
