package com.clinic.services;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.entities.AccompanimentScript;
import com.clinic.entities.Order;
import com.clinic.entities.User;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.OrderNotFoundExceprion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    @Transactional
    Order save(Order order);

    @Transactional
    void delete(Order order);

    List<Order> getAllOrders();

    List<Order> getAllOrdersByClientId(Long id)
            throws ClientNotFoundException;

    Order getOrderById(Long id)
            throws OrderNotFoundExceprion;

    AccompanimentScript getScriptByOrderId(long id)
            throws OrderNotFoundExceprion;

    @Transactional
    Order createBodyChanges(SimpleBodyChangesUpdate changesData)
            throws OrderNotFoundExceprion;
}
