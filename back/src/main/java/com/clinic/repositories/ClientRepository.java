package com.clinic.repositories;

import com.clinic.entities.Client;
import com.clinic.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPersonId(Long personId);

    Optional<Client> findByPerson_Id(Long personId);

}
