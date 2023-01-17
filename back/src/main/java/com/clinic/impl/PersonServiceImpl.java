package com.clinic.impl;

import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Passport;
import com.clinic.entities.Person;
import com.clinic.entities.User;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.PassportRepository;
import com.clinic.repositories.PersonRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.PassportService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

    private PassportService passportService;

    private PassportRepository passportRepository;
    private PersonRepository personRepository;



    @Autowired
    public PersonServiceImpl(
            PassportService ps,
            PassportRepository psr,
            PersonRepository pr)
    {
        this.passportService = ps;
        this.passportRepository = psr;
        this.personRepository = pr;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Person createPerson(SimplePersonRegistration personData)
            throws PassportConflictException
    {
        if (getPersonByPassportNum(personData.getPassport()).isPresent())
            throw new PassportConflictException(personData.getPassport());

        Person person = new Person();
        person.setName(personData.getName());
        person.setSurname(personData.getSurname());
        person.setPatronymic(personData.getPatronymic());
        person.setDateOfBirth(personData.getDateOfBirth());
        person = personRepository.save(person);

        Passport passport = new Passport();
        passport.setPassport(personData.getPassport());
        passport.setPerson(person);

        passport = passportRepository.save(passport);
        person.setPassports(Collections.singletonList(passport));

        return person;
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> getPersonByPassportNum(long passportNum)
    { return passportService.getPassportById(passportNum).map(Passport::getPerson); }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

}
