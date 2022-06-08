package com.clinic.services;

import com.clinic.entities.Person;
import com.clinic.entities.Specialization;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpecializationService {

    @Transactional
    Specialization save(Specialization specialization);

    @Transactional
    void delete(Specialization specialization);

    List<Specialization> getAllSpecializations();

}
