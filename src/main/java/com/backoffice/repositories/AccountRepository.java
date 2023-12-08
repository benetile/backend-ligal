package com.backoffice.repositories;

import com.backoffice.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByIdAndEmail(long id,String email);

    Optional<Account> findByIdAndTelephoneNumero(long id, String numero);

    List<Account> findByEmailContainsIgnoreCase(String email);

    List<Account> findByNomContainsIgnoreCase(String mot);

    List<Account> findByNomContainsIgnoreCaseOrPrenomContainsIgnoreCaseOrEmailContainsIgnoreCase(String nom, String prenom, String email);


}
