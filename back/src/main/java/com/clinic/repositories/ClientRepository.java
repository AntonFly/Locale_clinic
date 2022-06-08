package com.clinic.repositories;

import com.clinic.entities.Client;
import com.clinic.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {


}
