package com.clinic.repositories;

import com.clinic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByPersonId(Long personId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
