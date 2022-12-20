package com.clinic.repositories;

import com.clinic.entities.Stock;
import com.clinic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
