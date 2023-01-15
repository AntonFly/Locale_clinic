package com.clinic.repositories;

import com.clinic.entities.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByPersonId(Long personId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
