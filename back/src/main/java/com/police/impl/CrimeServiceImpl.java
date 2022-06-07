package com.police.impl;

import com.police.entities.Call;
import com.police.entities.Crime;
import com.police.entities.Location;
import com.police.entities.enums.CrimeType;
import com.police.repositories.CrimeRepository;
import com.police.repositories.PersonRepository;
import com.police.services.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrimeServiceImpl implements CrimeService {

    private PersonRepository personRepository;
    private CrimeRepository crimeRepository;

    @Autowired
    public CrimeServiceImpl(PersonRepository pr, CrimeRepository cr){
        this.crimeRepository = cr;
        this.personRepository = pr;
    }

    @Override
    public List<Crime> getCrimesByPerson(Long passportNumber) {
        return crimeRepository.findAllByCriminal(
                personRepository.getOne(passportNumber)
        );
    }

    @Override
    public List<Crime> getCrimesByType(CrimeType crimeType) {
        return crimeRepository.findAllByType(crimeType);
    }

    @Override
    public Crime createCrimeByCall(Call call, CrimeType type) {
        Crime crime = new Crime();
        crime.setDescription("Based on this call description: " + call.getDescription());
        crime.setType(type);
        crime.setCrimeLocation(call.getCallLocation());
        crimeRepository.save(crime);
        return crime;
    }

    @Override
    public List<Crime> getCrimesByLocation(Location location) {
        return crimeRepository.findAllByCrimeLocation(location);
    }

    @Override
    public List<Crime> getAllCrimes() {
        return crimeRepository.findAll();
    }
}
