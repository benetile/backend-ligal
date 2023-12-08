package com.backoffice.repositories;

import com.backoffice.entites.Users.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ERole,Long> {

    Optional<ERole> findByNom(String nom);
}
