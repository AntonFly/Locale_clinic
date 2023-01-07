package com.clinic.repositories;

import com.clinic.entities.BodyChange;
import com.clinic.entities.Client;
import com.clinic.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyChangeRepository extends JpaRepository<BodyChange, Long> {

}
