package com.clinic.impl;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.repositories.ModificationRepository;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.ScenarioService;
import com.clinic.services.SpecializationService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ModificationRepository modificationRepository;

    private final ScenarioRepository scenarioRepository;

    private final SpecializationRepository specializationRepository;

    private final SpecializationService specializationService;


    @Autowired
    public ScenarioServiceImpl(
            ModificationRepository mr,
            ScenarioRepository sr,
            SpecializationService ss,
            SpecializationRepository slr
    )
    {
        this.modificationRepository = mr;
        this.scenarioRepository = sr;
        this.specializationService = ss;
        this.specializationRepository = slr;
    }

    private SpecializationMissingException GenerateException(long specId)
    { return new SpecializationMissingException("There is no scenario for specialization with id: " + specId); }

    private ModificationMissingException GenerateModException(long modId)
    { return  new ModificationMissingException("There is no scenario for modification with id: " + modId); }

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
    public List<Scenario> getAllScenariosBySpecId(long specId)
            throws SpecializationMissingException
    {
        return scenarioRepository.findAllBySpecializationId(specId)
                .orElseThrow(() -> GenerateException(specId));
    }

    @Override
    public List<Modification> getAllModificationsBySpecOrderedByRisk(int specId)
            throws SpecializationMissingException
    {
        List<Scenario> scenarios = scenarioRepository.findAllBySpecializationId(specId)
                .orElseThrow(() -> GenerateException(specId));

       return scenarios.stream()
                .map(Scenario::getModifications)
                .flatMap(Set::stream)
               .sorted(Comparator.comparing(Modification::getChance))
               .collect(Collectors.toList());
    }

    @Override
    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationMissingException, ModificationMissingException
    {
        Specialization specialization = specializationRepository.findById(scenarioData.getSpecId())
                .orElseThrow(() -> GenerateException(scenarioData.getSpecId()));

        Set<Modification> modifications = new TreeSet<>();
        for (Integer modId : scenarioData.getModIds())
            modifications.add(modificationRepository.findById(modId.longValue()).orElseThrow(() -> GenerateModException(modId)));

        Scenario scenario = new Scenario();
        scenario.setSpecialization(specialization);
        scenario.setModifications(modifications);

        return scenarioRepository.save(scenario);
    }
    @Override
    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationMissingException
    {
        Set<Modification> modifications = new HashSet<>() ;
        List<Scenario> scenarios = scenarioRepository.findAllBySpecialization_Id(specId)
                .orElseThrow(() -> GenerateException(specId));


        scenarios.forEach((item) -> modifications.addAll(item.getModifications()));


        return modifications;//modificationRepository.findBySpecializations(specialization);
    }
}
