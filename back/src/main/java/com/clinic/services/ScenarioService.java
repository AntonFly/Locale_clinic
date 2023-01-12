package com.clinic.services;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleScenarioUpdate;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.exceptions.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ScenarioService {

    @Transactional(rollbackFor = Exception.class)
    Scenario save(Scenario scenario);

    @Transactional(rollbackFor = Exception.class)
    void delete(Scenario scenario);

    List<Scenario> getAllScenarios();

//    Set<Scenario> getAllScenariosBySpec(String specName)
//            throws SpecializationMissingException;

    public Set<Modification> getAllModificationsBySpec(long specId)
            throws SpecializationNotFoundException;

    public List<Modification> getAllModificationsBySpecOrderedByRisk(long specId)
            throws SpecializationNotFoundException;

    @Transactional(rollbackFor = Exception.class)
    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, ScenarioOrderException;

    @Transactional(rollbackFor = Exception.class)
    public Scenario updateScenario(SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, UnspecifiedModScenarioException, UnknownModScenarioException;

}
