package com.backoffice.repositories;

import com.backoffice.entites.annonce.TypeAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeAnnounceRepository extends JpaRepository<TypeAnnonce, Long> {

    Optional<TypeAnnonce> findByNom(String nom);

    Optional<TypeAnnonce> findByIdAndNom(long id, String nom);

    Optional<TypeAnnonce> findByNomIgnoreCase(String nom);

}
