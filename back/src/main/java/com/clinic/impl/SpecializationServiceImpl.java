package com.clinic.impl;

import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.SpecializationNotFoundException;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private SpecializationRepository specializationRepository;


    @Autowired
    public SpecializationServiceImpl(SpecializationRepository sr){
        this.specializationRepository = sr;
    }

    @Override
    public Specialization save(Specialization specialization) {
        specialization = specializationRepository.save(specialization);
        specializationRepository.flush();
        return specialization;
    }

    @Override
    public void delete(Specialization specialization) {
        specializationRepository.delete(specialization);
    }

    @Override
    public Specialization getSpecById(Long specId) throws SpecializationNotFoundException
    {
        return specializationRepository.findById(specId)
                .orElseThrow(()->new SpecializationNotFoundException(specId));
    }

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Override
    public List<Modification> getAllModificationsBySpec(int specId) throws SpecializationNotFoundException {
        return null;
    }




}
