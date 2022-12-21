package com.clinic.impl;

import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.repositories.ModificationRepository;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.services.ModificationService;
import com.clinic.services.ScenarioService;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository scenarioRepository;

    private final SpecializationService specializationService;


    @Autowired
    public ScenarioServiceImpl(
            ScenarioRepository sr,
            SpecializationService ss
    ){
        this.scenarioRepository = sr;
        this.specializationService = ss;
    }

    @Override
    public Scenario save(Scenario scenario) {
        scenario = scenarioRepository.save(scenario);
        scenarioRepository.flush();
        return scenario;
    }

    @Override
    public void delete(Scenario scenario) {
        scenarioRepository.delete(scenario);
    }

    @Override
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    @Override
    public List<Scenario> getAllScenariosBySpec(String specName)
            throws SpecializationMissingException
    {
        Specialization specialization = specializationService.getSpecByName(specName);
        return null;
    }
    @Override
    public List<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException
    {
        Set<Modification> modifications;
        List<Scenario> scenarios = scenarioRepository.findAllBySpecialization_Id(specId)
                .orElseThrow(()-> new SpecializationMissingException("There is no specialization with id: "+specId));

        //TODO scenarios.forEach((item) -> modifications. );


        return null;//modificationRepository.findBySpecializations(specialization);
    }
}
