package com.clinic.impl;

import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.SpecializationMissingException;
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

    private SpecializationService specializationService;


    @Autowired
    public ModificationServiceImpl(
            ModificationRepository mr,
            SpecializationService sr
    ){
        this.modificationRepository = mr;
        this.specializationService = sr;
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
    public List<Modification> getAllModificationsBySpec(SimpleSpecializationRegistration specializationData)
            throws SpecializationMissingException
    {
        Specialization specialization = specializationService.getSpecByName(specializationData.getName());
        return modificationRepository.findBySpecializations(specialization);
    }
}
