package com.clinic.services;

import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.SpecializationMissingException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ScenarioService {

    @Transactional
    Scenario save(Scenario scenario);

    @Transactional
    void delete(Scenario scenario);

    List<Scenario> getAllScenarios();

    List<Scenario> getAllScenariosBySpec(String specName)
            throws SpecializationMissingException;

    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException;

}
