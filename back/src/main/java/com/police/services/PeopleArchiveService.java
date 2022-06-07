package com.police.services;

import com.police.entities.Person;

import java.util.List;

public interface PeopleArchiveService {

    List<Person> getPeopleByAttributes(Long passportNumber, String name, String surname);

    Person getPersonByPassportNumber(long passportNumber);

}
