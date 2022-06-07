package com.police.repositories;

import com.police.entities.Crime;
import com.police.entities.Location;
import com.police.entities.Person;
import com.police.entities.enums.CrimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrimeRepository extends JpaRepository<Crime, Long> {

    List<Crime> findAllByCriminal(Person person);

    List<Crime> findAllByType(CrimeType crimeType);

    List<Crime> findAllByCrimeLocation(Location location);

}
