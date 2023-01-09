package com.clinic.services;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.entities.AccompanimentScript;
import com.clinic.entities.BodyChange;
import com.clinic.entities.Order;
import com.clinic.exceptions.BodyChangeNotFoundException;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.OrderNotFoundException;
import com.clinic.exceptions.ScenarioNotFoundException;
import com.clinic.exceptions.PassportNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface OrderService {

    @Transactional
    Order save(Order order);

    @Transactional
    void delete(Order order);

    List<Order> getAllOrders();

    Set<Order> getAllOrdersByPassport(long passport)
            throws PassportNotFoundException, ClientNotFoundException;

    Order getOrderById(Long id)
            throws OrderNotFoundException;

    AccompanimentScript getScriptByOrderId(long id)
            throws OrderNotFoundException, ScenarioNotFoundException;

    @Transactional
    Order createBodyChanges(SimpleBodyChangesUpdate changesData)
            throws OrderNotFoundException;

    List<BodyChange> getBodyChanges(long orderId)
            throws OrderNotFoundException;

    Boolean dropBodyChange(long bodyChangeId)
            throws BodyChangeNotFoundException;
}

