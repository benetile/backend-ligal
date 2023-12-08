package com.backoffice.repositories;

import com.backoffice.entites.Actualite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActualiteRepositroy extends JpaRepository<Actualite,Long> {
    Optional<Actualite> findByTitreIgnoreCase(String titre);
}
