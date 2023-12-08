package com.backoffice.repositories;

import com.backoffice.entites.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAccountId(long id);

    Optional<User> findByUsername(String username);
}
