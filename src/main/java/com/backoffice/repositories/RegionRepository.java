package com.backoffice.repositories;

import com.backoffice.entites.adresse.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {

    Optional<Region> findByNomIgnoreCase(String nom);

    List<Region> findByNomContains(String c);

}
