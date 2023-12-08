package com.backoffice.repositories;

import com.backoffice.entites.adresse.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {

    Optional<Ville> findByNom(String nom);

    Optional<Ville> findByCodePostal(String cp);

    List<Ville> findByCodePostalStartsWith(String cp);
    List<Ville> findByNomStartsWith(String nom);


    Optional<Ville> findByNomIgnoreCase(String nom);
    Optional<Ville> findByNomIgnoreCaseAndDepartementId(String nom, long id);
    Optional<Ville> findByNomIgnoreCaseAndCodePostal(String nom, String cp);


    List<Ville> findByNomContainsIgnoreCase(String nom);

    /*List<Ville> findByProvinceIdAndNomContainsIgnoreCase(long id, String nom);

    Optional<Ville> findByNomAndProvinceId(String nom, long id);*/

}
