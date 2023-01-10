package com.clinic.services;

import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Person;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    @Transactional(rollbackFor = Exception.class)
    Person createPerson(SimplePersonRegistration person)
            throws PassportConflictException, PersonConflictException;

    @Transactional(rollbackFor = Exception.class)
    void delete(Person person);

    Optional<Person> getPersonById(Long id);

    Optional<Person> getPersonByPassportNum(long passportNum);

    List<Person> getAllPersons();



}
