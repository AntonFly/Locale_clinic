package com.police.repositories;

import com.police.entities.Role;
import com.police.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    void deleteByUsername(String username);

}
