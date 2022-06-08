package com.clinic.impl;

import com.clinic.entities.Person;
import com.clinic.entities.User;
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
    public Person save(Person person) {
        //kostil'
        person.setDateOfBirth(new Date(3, 3, 13));
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
