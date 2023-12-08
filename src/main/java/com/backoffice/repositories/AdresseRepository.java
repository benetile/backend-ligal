package com.backoffice.repositories;

import com.backoffice.entites.adresse.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    Optional<Adresse> findByAdresseAndNumeroAndVilleNom(String adresse, String numero,String nom);
    Optional<Adresse> findByAdresseAndNumeroAndVilleCodePostal(String adresse, String numero,String cp);

    Optional<Adresse> findByAdresseIgnoreCaseAndCommuneId(String adresse, long id);
    Optional<Adresse> findByAdresseIgnoreCaseAndNumeroAndVilleCodePostal(String adresse, String numero, String cp);


    Optional<Adresse> findByAdresseIgnoreCaseAndNumeroAndCommuneId(String adresse, String numero, long id);
}
