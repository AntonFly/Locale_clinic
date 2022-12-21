package com.clinic.services;

import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.SpecializationMissingException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModificationService {

    @Transactional
    Modification save(Modification specialization);

    @Transactional
    void delete(Modification specialization);

    Modification getModificationByName(String name)
        throws ModificationMissingException;

    List<Modification> getAllModifications();




}
