package com.clinic.repositories;

import com.clinic.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    Optional<Specialization> findByName(String name);

}
