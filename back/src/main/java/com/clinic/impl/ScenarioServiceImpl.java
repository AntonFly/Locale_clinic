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

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private ScenarioRepository scenarioRepository;

    private SpecializationService specializationService;


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
    public List<Scenario> getAllScenariosBySpec(SimpleSpecializationRegistration specializationData)
            throws SpecializationMissingException
    {
        Specialization specialization = specializationService.getSpecByName(specializationData.getName());
        return scenarioRepository.findBySpecialization(specialization);
    }
}
