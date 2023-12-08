package com.backoffice.repositories;

import com.backoffice.entites.annonce.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {

    Optional<Annonce> findByReference(String reference);
    List<Annonce> findByAccountId(Long id);
    List<Annonce> findByTypeAnnonceId(long id);
    List<Annonce> findByTypeAnnonceNom(String nom);
    Annonce findFirstByOrderByMontantDesc();
    List<Annonce> findByMontantBetween(BigDecimal min, BigDecimal max);
    List<Annonce> findByTypeAnnonceIdAndMontantBetween(long id,BigDecimal min, BigDecimal max);

    List<Annonce> findByVilleAndTitreContains(String ville, String titre);
    List<Annonce> findByTitreContains( String titre);

    List<Annonce> findByVilleIgnoreCase(String ville);


}
