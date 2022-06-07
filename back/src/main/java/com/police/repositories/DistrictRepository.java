package com.police.repositories;

import com.police.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
