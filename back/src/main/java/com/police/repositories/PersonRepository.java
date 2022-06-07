package com.police.repositories;

import com.police.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM person WHERE passport_number = ?1 AND name ~* ?2 AND surname ~* ?3")
    public List<Person> findAllByPassportNumberAndNameAndSurname(Long passportNumber, String name, String surname);

    @Query(nativeQuery = true, value = "SELECT * FROM person WHERE name ~* ?1 AND surname ~* ?2")
    public List<Person> findAllByNameAndSurname(String name, String surname);

    Person findByPassportNumber(long passportNumber);
}
