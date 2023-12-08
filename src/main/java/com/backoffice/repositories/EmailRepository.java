package com.backoffice.repositories;

import com.backoffice.entites.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {

    List<Email> findByProgrammerTrue();
    List<Email> findByEnvoyeFalse();
}
