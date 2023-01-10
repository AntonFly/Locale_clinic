package com.clinic.services;

import com.clinic.entities.Modification;
import com.clinic.exceptions.ModificationNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModificationService {

    @Transactional
    Modification save(Modification specialization);

    @Transactional
    void delete(Modification specialization);

    Modification getModificationById(Long modId)
            throws ModificationNotFoundException;

    List<Modification> getAllModifications();




}
