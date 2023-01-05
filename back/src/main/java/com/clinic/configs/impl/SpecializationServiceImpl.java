package com.clinic.configs.impl;

import com.clinic.entities.Modification;
import com.clinic.entities.Person;
import com.clinic.entities.Specialization;
import com.clinic.entities.User;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.repositories.PersonRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.PersonService;
import com.clinic.services.SpecializationService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Specialization getSpecByName(String name) throws SpecializationMissingException
    {
        Optional<Specialization> specialization = specializationRepository.findByName(name);
        if (specialization.isPresent())
            return specialization.get();

        throw new SpecializationMissingException(
                "No specialization with " +
                        (name.isEmpty() ?
                                "empty name" :
                                ("the name " + name))
                        + " was found");
    }

    @Override
    public Specialization getSpecById(Long specId) throws SpecializationMissingException {
        return specializationRepository.findById(specId)
                .orElseThrow(()->new SpecializationMissingException("Не было найдено специализации с id: "+ specId));
    }

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Override
    public List<Modification> getAllModificationsBySpec(int specId) throws SpecializationMissingException {
        return null;
    }




}
