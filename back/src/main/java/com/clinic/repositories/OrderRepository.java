package com.clinic.repositories;

import com.clinic.entities.Client;
import com.clinic.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByClient_Person_Id(Long id);

}
