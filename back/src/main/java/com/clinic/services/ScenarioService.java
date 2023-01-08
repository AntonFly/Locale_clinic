package com.clinic.services;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleScenarioUpdate;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.exceptions.ModificationNotFoundException;
import com.clinic.exceptions.ScenarioNotFoundException;
import com.clinic.exceptions.SpecializationNotFoundException;
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
            throws SpecializationNotFoundException;

    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationNotFoundException;

    public List<Modification> getAllModificationsBySpecOrderedByRisk(int specId)
            throws SpecializationNotFoundException;

    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationNotFoundException, ModificationNotFoundException;

    public Scenario updateScenario(SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, SpecializationNotFoundException, ModificationNotFoundException;

}
