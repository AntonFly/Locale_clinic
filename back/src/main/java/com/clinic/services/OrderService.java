package com.clinic.services;

import com.clinic.entities.Order;
import com.clinic.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    @Transactional
    Order save(Order order);

    @Transactional
    void delete(Order order);

    List<Order> getAllOrders();

}
