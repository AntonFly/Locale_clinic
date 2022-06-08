package com.clinic.services;

import com.clinic.entities.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonService {

    @Transactional
    Person save(Person person);

    @Transactional
    void delete(Person person);

    Optional<Person> getPersonById(Long id);

    List<Person> getAllPersons();

}
