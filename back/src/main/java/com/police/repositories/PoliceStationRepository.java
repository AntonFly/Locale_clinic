package com.police.repositories;

import com.police.entities.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PoliceStationRepository extends JpaRepository<PoliceStation, Long> {
}
