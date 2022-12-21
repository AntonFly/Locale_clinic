package com.clinic.repositories;

import com.clinic.entities.Passport;
import com.clinic.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> getPassportByPassport(Long id);

    List<Passport> findAllByPerson(Person person);

}
