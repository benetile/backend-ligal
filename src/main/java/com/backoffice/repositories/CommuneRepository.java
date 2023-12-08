package com.backoffice.repositories;

import com.backoffice.entites.adresse.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {

    Optional<Commune> findByNom(String nom);

    List<Commune> findByVilleId(long id);

    List<Commune> findByNomContainsIgnoreCase(String nom);

    List<Commune> findByVilleIdAndNomContainsIgnoreCase(long id, String nom);
    Optional<Commune> findByNomIgnoreCaseAndVilleId(String nom, long id);

}
