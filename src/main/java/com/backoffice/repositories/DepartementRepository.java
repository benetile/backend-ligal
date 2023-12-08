package com.backoffice.repositories;

import com.backoffice.entites.adresse.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Long> {

    Optional<Departement> findByNomAndCode(String nom,String code);

    Optional<Departement> findByCode(String code);

    Optional<Departement> findByCodeStartsWith(String code);
}
