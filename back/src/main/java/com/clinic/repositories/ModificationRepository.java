package com.clinic.repositories;

import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModificationRepository extends JpaRepository<Modification, Long> {

    //List<Modification> getAllModificationsBySpecialization(Specialization specialization);

}
