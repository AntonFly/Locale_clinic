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
    public Modification getModificationByName(String name)
            throws ModificationMissingException {
        Optional<Modification> modification = modificationRepository.findByName(name);
        if (modification.isPresent())
            return modification.get();

        throw new ModificationMissingException(
                "There is no modification associated with " +
                (name.isEmpty() ? "empty name" : ("name: " + name)));
    }

    @Override
    public Modification getModificationById(Long modId) throws ModificationMissingException {
        return modificationRepository.findById(modId)
                .orElseThrow(()-> new ModificationMissingException("Не было найдено модификаций с id: "+ modId));
    }

    @Override
    public List<Modification> getAllModifications() {
        return modificationRepository.findAll();
    }


}
