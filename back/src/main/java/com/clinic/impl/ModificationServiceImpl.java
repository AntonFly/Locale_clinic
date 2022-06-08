package com.clinic.impl;

import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.repositories.ModificationRepository;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.ModificationService;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModificationServiceImpl implements ModificationService {

    private ModificationRepository modificationRepository;


    @Autowired
    public ModificationServiceImpl(ModificationRepository mr){
        this.modificationRepository = mr;
    }

    @Override
    public Modification save(Modification modification) {
        modification = modificationRepository.save(modification);
        modificationRepository.flush();
        return modification;
    }

    @Override
    public void delete(Modification modification) {
        modificationRepository.delete(modification);
    }

    @Override
    public List<Modification> getAllModifications() {
        return modificationRepository.findAll();
    }

    @Override
    public List<Modification> getAllModificationsBySpec(Specialization specialization)
    {
        return null;//modificationRepository.getAllModificationsBySpecialization(specialization);
    }
}
