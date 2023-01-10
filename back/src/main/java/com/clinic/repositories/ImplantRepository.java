package com.clinic.repositories;

import com.clinic.entities.BodyChange;
import com.clinic.entities.Implant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImplantRepository extends JpaRepository<Implant, Long> {
}
