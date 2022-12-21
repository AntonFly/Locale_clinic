package com.clinic.services;

import com.clinic.entities.Modification;
import com.clinic.entities.Person;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.SpecializationMissingException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpecializationService {

    @Transactional
    Specialization save(Specialization specialization);

    @Transactional
    void delete(Specialization specialization);

    Specialization getSpecByName(String name) throws SpecializationMissingException;

    List<Specialization> getAllSpecializations();

    List<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException;

}
