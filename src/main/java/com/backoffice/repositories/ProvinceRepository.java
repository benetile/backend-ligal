package com.backoffice.repositories;

import com.backoffice.entites.adresse.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province,Long> {

    Optional<Province> findByNom(String nom);

    List<Province> findByNomContainsIgnoreCase(String nom);
}
