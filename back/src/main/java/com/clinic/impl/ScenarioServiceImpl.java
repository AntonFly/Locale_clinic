package com.clinic.impl;

import com.clinic.dto.SimpleModPriority;
import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleScenarioUpdate;
import com.clinic.entities.*;
import com.clinic.entities.keys.ModificationScenarioId;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.ScenarioService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final OrderRepository orderRepository;
    private final ModificationRepository modificationRepository;
    private final ModificationScenarioRepository modificationScenarioRepository;

    private final ScenarioRepository scenarioRepository;

    private final SpecializationRepository specializationRepository;


    @Autowired
    public ScenarioServiceImpl(
            OrderRepository or,
            ModificationRepository mr,
            ModificationScenarioRepository msr,
            ScenarioRepository sr,
            SpecializationRepository slr
    )
    {
        this.orderRepository = or;
        this.modificationRepository = mr;
        this.modificationScenarioRepository = msr;
        this.scenarioRepository = sr;
        this.specializationRepository = slr;
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
    public List<Modification> getAllModificationsBySpecOrderedByRisk(int specId)
            throws SpecializationNotFoundException
    {
        return getAllModificationsBySpec(specId).stream()
               .sorted(Comparator.comparing(Modification::getChance))
               .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Scenario createScenario(SimpleScenarioRegistration scenarioData)
            throws SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, ScenarioOrderException
    {

        Specialization specialization = specializationRepository.findById(scenarioData.getSpecId())
                .orElseThrow(() -> new SpecializationNotFoundException(scenarioData.getSpecId()));

        Order order = orderRepository.findById(scenarioData.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(scenarioData.getOrderId()));

        if (scenarioRepository.existsByOrder(order))
            throw new ScenarioOrderException(order.getId());

        Scenario scenario = new Scenario();
        scenario.setSpecialization(specialization);
        scenario.setOrder(order);
        scenario = scenarioRepository.save(scenario);

        List<ModificationScenario> modificationScenarios = new ArrayList<>();
        for (long modId : scenarioData.getMods())
        {
            Modification modification = modificationRepository.findById(modId)
                    .orElseThrow(() -> new ModificationNotFoundException(modId));

            ModificationScenario modificationScenario = new ModificationScenario();
            ModificationScenarioId modificationScenarioId = new ModificationScenarioId();
            modificationScenarioId.setScenarioId(scenario.getId());
            modificationScenarioId.setModificationId(modification.getId());
            modificationScenario.setId(modificationScenarioId);
            modificationScenario.setModification(modification);
            modificationScenario.setScenario(scenario);
            modificationScenarios.add(modificationScenario);
        }

        scenario.setModificationScenarios(modificationScenarioRepository.saveAll(modificationScenarios));

        return scenario;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Scenario updateScenario(SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, ModificationNotFoundException, UnspecifiedModScenarioException, UnknownModScenarioException {
        Scenario scenario = scenarioRepository.findById(updateData.getScenarioId())
                .orElseThrow(() -> new ScenarioNotFoundException(updateData.getScenarioId()));

        List<ModificationScenario> modificationScenarios = scenario.getModificationScenarios();

        for (ModificationScenario modificationScenario : modificationScenarios)
            if (updateData.getMods().stream().noneMatch(x -> x.getId() == modificationScenario.getModification().getId()))
                throw new UnspecifiedModScenarioException(modificationScenario.getModification().getId());

        for (SimpleModPriority modPriority: updateData.getMods())
        {
            ModificationScenario modificationScenario = modificationScenarios.stream()
                    .filter(x -> x.getModification().getId() == modPriority.getId())
                    .findFirst()
                    .orElseThrow(() -> new UnknownModScenarioException(modPriority.getId()));

            modificationScenario.setPriority(modPriority.getPriority());
            modificationScenarioRepository.save(modificationScenario);
        }

        scenario.setModificationScenarios(modificationScenarios);
        return scenario;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<Modification> getAllModificationsBySpec(int specId)
            throws SpecializationNotFoundException
    {
        Specialization specialization = specializationRepository.findById(specId)
                .orElseThrow(() -> new SpecializationNotFoundException(specId));

        return specialization.getModifications();
    }
}
