package com.backoffice.services;

import com.backoffice.entites.Account;
import com.backoffice.entites.Telephone;
import com.backoffice.repositories.AccountRepository;
import com.backoffice.repositories.TelephoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TelephoneService implements TelephoneRepository {

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(TelephoneService.class);

    public  Telephone verifcationTelephone(Telephone telephone){
        logger.info("Début de verifiaction du telephone ");
        String numero = telephone.getNumero();
        if(Objects.isNull(telephone.getId())){
            logger.info("Création d'un nouveau numéro de telephone");
            if(findByNumero(numero).isPresent()){
                logger.info("Le numéro de telephon est déjà utilisé par un autre utilisateur : "+numero);
                throw new IllegalArgumentException("Le numéro de telephon est déjà utilisé par un autre utilisateur");
            }else {
                telephone.setPrefix("+243");
                telephone.setDateDeCreation(new Date());
                telephoneRepository.save(telephone);
                logger.info("Fin de la création d'un nouveau numéro poratent l'id : "+telephone.getId());
            }
        }else{
            logger.info("Modification d'un numéro de téléphone ");
            if (findByNumero(numero).isEmpty()){
                telephone.setDateDeModification(new Date());
                telephoneRepository.save(telephone);
                logger.info("Fin de la mise à jour du telephone ");
            }
        }
        return  telephone;
    }

    @Override
    public Optional<Telephone> findByNumero(String numero) {
        return telephoneRepository.findByNumero(numero);
    }

    @Override
    public List<Telephone> findByNumeroContains(String numero) {
        return null;
    }

    @Override
    public List<Telephone> findAll() {
        return null;
    }

    @Override
    public List<Telephone> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Telephone> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Telephone> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Telephone entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Telephone> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Telephone> S save(S entity) {


        return telephoneRepository.save(entity);
    }

    @Override
    public <S extends Telephone> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Telephone> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Telephone> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Telephone> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Telephone> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Telephone getOne(Long aLong) {
        return null;
    }

    @Override
    public Telephone getById(Long aLong) {
        return null;
    }

    @Override
    public Telephone getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Telephone> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Telephone> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Telephone> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Telephone> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Telephone> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Telephone> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Telephone, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
