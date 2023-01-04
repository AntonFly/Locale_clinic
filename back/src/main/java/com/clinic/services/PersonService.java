package com.clinic.services;

import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Person;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonService {

    @Transactional
    Person save(SimplePersonRegistration person) throws PersonConflictException, PassportConflictException;

    @Transactional
    void delete(Person person);

    Optional<Person> getPersonById(Long id);

    List<Person> getAllPersons();



}
