package com.clinic.repositories;

import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ModificationRepository extends JpaRepository<Modification, Long> {

    Optional<Modification> findByName(String name);

    List<Modification> findBySpecializations_Name(String name);
    List<Modification> findBySpecializations(Specialization specialization);

}
