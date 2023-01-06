package com.clinic.repositories;

import com.clinic.entities.Stock;
import com.clinic.entities.User;
import com.clinic.entities.keys.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    boolean existsByStockId(StockId stockId);

    Optional<Stock> findByStockId(StockId stockId);

}
