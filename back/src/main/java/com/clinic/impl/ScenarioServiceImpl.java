package com.clinic.impl;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleScenarioUpdate;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationNotFoundException;
import com.clinic.exceptions.ScenarioNotFoundException;
import com.clinic.exceptions.SpecializationNotFoundException;
import com.clinic.repositories.ModificationRepository;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ModificationRepository modificationRepository;

    private final ScenarioRepository scenarioRepository;

    private final SpecializationRepository specializationRepository;


    @Autowired
    public ScenarioServiceImpl(
            ModificationRepository mr,
            ScenarioRepository sr,
            SpecializationRepository slr
    )
    {
        this.modificationRepository = mr;
        this.scenarioRepository = sr;
        this.specializationRepository = slr;
    }

    private SpecializationNotFoundException GenerateSpecException(long specId)
    { return new SpecializationNotFoundException("There is no scenario for specialization with id: " + specId); }

    private ModificationNotFoundException GenerateModException(long modId)
    { return new ModificationNotFoundException("There is no scenario for modification with id: " + modId); }

    private ScenarioNotFoundException GenerateScenException(long scenId)
    { return new ScenarioNotFoundException("There is no scenario with id: " + scenId); }

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
    public List<Modification> getAllModificationsBySpecOrderedByRisk(int specId)
            throws SpecializationNotFoundException
    {
        return getAllModificationsBySpec(specId).stream()
               .sorted(Comparator.comparing(Modification::getChance))
               .collect(Collectors.toList());
    }

    @Override
    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationNotFoundException, ModificationNotFoundException
    {
        Set<Modification> modifications = new HashSet<>();
        for (Integer modId : scenarioData.getModIds())
            modifications.add(modificationRepository.findById(modId.longValue())
                    .orElseThrow(() -> GenerateModException(modId)));

        Scenario scenario = new Scenario();
        //scenario.setModifications(modifications);

        return scenarioRepository.save(scenario);
    }
    @Override
    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationNotFoundException
    {
        Specialization specialization = specializationRepository.findById(specId)
                .orElseThrow(() -> GenerateSpecException(specId));

        return specialization.getModifications();
    }

    @Override
    public Scenario updateScenario(SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, SpecializationNotFoundException, ModificationNotFoundException
    {
        Scenario scenario = scenarioRepository.findById(updateData.getScenarioId())
                .orElseThrow(() -> GenerateScenException(updateData.getScenarioId()));

        Specialization specialization = specializationRepository.findById(updateData.getSpecId())
                .orElseThrow(() -> GenerateSpecException(updateData.getSpecId()));

        Set<Modification> modifications = new HashSet<>();
        for (Long modId : updateData.getModIds())
            modifications.add(modificationRepository.findById(modId)
                    .orElseThrow(() -> GenerateModException(modId)));

        //scenario.setModifications(modifications);

        return scenarioRepository.save(scenario);
    }
}
