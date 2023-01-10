package com.clinic.services;

import com.clinic.entities.Passport;
import com.clinic.entities.Person;
import com.clinic.exceptions.PassportConflictException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    @Transactional(rollbackFor = Exception.class)
    Passport save(Person person, long passport) throws PassportConflictException;

    @Transactional(rollbackFor = Exception.class)
    void delete(Passport person);

    boolean exists(long id);

    Optional<Passport> getPassportById(Long id);

    List<Passport> getAllPassport();

    List<Passport> findAllByPerson(Person person);
}
