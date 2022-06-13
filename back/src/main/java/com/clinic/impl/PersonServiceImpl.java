package com.clinic.impl;

import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Person;
import com.clinic.entities.User;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.PersonRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;


    @Autowired
    public PersonServiceImpl(PersonRepository pr){
        this.personRepository = pr;
    }

    @Override
    public Person save(SimplePersonRegistration personData)
            throws PersonConflictException {
        Person person = new Person();
        person.setId(personData.getPassport());
        person.setName(personData.getName());
        person.setSurname(personData.getSurname());
        person.setPatronymic(personData.getPatronymic());
        person.setDateOfBirth(personData.getDateOfBirth());

        Optional<Person> optionalPerson = personRepository.getPersonById(person.getId());
        if (optionalPerson.isPresent())
            if (!optionalPerson.get().equals(person))
                throw new PersonConflictException(
                        "There is already a person with this passport: " +
                                person.getId() +
                                ", but different parameters");
            else
                return optionalPerson.get();

        person = personRepository.save(person);
        personRepository.flush();
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
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

}
