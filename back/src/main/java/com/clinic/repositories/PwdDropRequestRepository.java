package com.clinic.repositories;

import com.clinic.entities.Person;
import com.clinic.entities.PwdDropRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PwdDropRequestRepository extends JpaRepository<PwdDropRequest, Long> {

}
