package com.clinic.impl;

import com.clinic.entities.Modification;
import com.clinic.exceptions.ModificationNotFoundException;
import com.clinic.repositories.ModificationRepository;
import com.clinic.services.ModificationService;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Modification getModificationById(Long modId) throws ModificationNotFoundException {
        return modificationRepository.findById(modId)
                .orElseThrow(()-> new ModificationNotFoundException(modId));
    }

    @Override
    public List<Modification> getAllModifications() {
        return modificationRepository.findAll();
    }


}
