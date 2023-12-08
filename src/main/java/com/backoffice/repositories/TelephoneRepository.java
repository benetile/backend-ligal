package com.backoffice.repositories;

import com.backoffice.entites.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone,Long> {
    Optional<Telephone> findByNumero(String numero);

    List<Telephone> findByNumeroContains(String numero);

}
