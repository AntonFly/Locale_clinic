package com.clinic.services;

import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModificationService {

    @Transactional
    Modification save(Modification specialization);

    @Transactional
    void delete(Modification specialization);

    List<Modification> getAllModifications();

    List<Modification> getAllModificationsBySpec(Specialization specialization);


}
