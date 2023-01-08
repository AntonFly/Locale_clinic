package com.clinic.repositories;

import com.clinic.entities.Role;
import com.clinic.entities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole name);

}
