package com.clinic.services;

import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.SpecializationNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SpecializationService {

    @Transactional(rollbackFor = Exception.class)
    Specialization save(Specialization specialization);

    @Transactional(rollbackFor = Exception.class)
    void delete(Specialization specialization);

    Specialization getSpecById (Long specId) throws SpecializationNotFoundException;

    List<Specialization> getAllSpecializations();

    List<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationNotFoundException;

}
