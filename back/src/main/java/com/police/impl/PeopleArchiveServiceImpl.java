package com.police.impl;

import com.police.entities.Person;
import com.police.repositories.PersonRepository;
import com.police.services.PeopleArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleArchiveServiceImpl implements PeopleArchiveService {

    private PersonRepository personRepository;

    @Autowired
    public PeopleArchiveServiceImpl(PersonRepository pr){
        this.personRepository = pr;
    }

    @Override
    public List<Person> getPeopleByAttributes(Long passportNumber, String name, String surname) {
        //return personRepository.findAllByPassportNumberAndNameAndSurname(passportNumber, name, surname);
        String regexpName = "^" + name;
        String regexpSurname = "^" + surname;
        if (passportNumber != null)
            return personRepository.findAllByPassportNumberAndNameAndSurname(passportNumber, regexpName, regexpSurname);
        else
            return personRepository.findAllByNameAndSurname(regexpName, regexpSurname);

    }

    @Override
    public Person getPersonByPassportNumber(long passportNumber) {
        return personRepository.findByPassportNumber(passportNumber);
    }
}
