package com.clinic.services;

import com.clinic.dto.SimpleScenarioRegistration;
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

//    Set<Scenario> getAllScenariosBySpec(String specName)
//            throws SpecializationMissingException;

    List<Scenario> getAllScenariosBySpecId(long specId)
            throws SpecializationMissingException;

    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException;

    public List<Modification> getAllModificationsBySpecOrderedByRisk(int specId)
            throws SpecializationMissingException;

    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationMissingException, ModificationMissingException;

}
