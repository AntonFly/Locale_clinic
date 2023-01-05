package com.clinic.configs.impl;

import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.services.ScenarioService;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException
    {
        Set<Modification> modifications = new HashSet<>() ;
        List<Scenario> scenarios = scenarioRepository.findAllBySpecialization_Id(specId)
                .orElseThrow(()-> new SpecializationMissingException("There is no scenario for specialization with id: "+specId));


        scenarios.forEach((item) -> modifications.addAll(item.getModifications()));


        return modifications;//modificationRepository.findBySpecializations(specialization);
    }
}
