package com.clinic.repositories;

import com.clinic.entities.Modification;
import com.clinic.entities.Order;
import com.clinic.entities.Scenario;
import com.clinic.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    boolean existsByOrder(Order order);

    Scenario getScenarioById(long id);


}
