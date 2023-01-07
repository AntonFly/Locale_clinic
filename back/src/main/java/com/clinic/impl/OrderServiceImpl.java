package com.clinic.impl;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.OrderNotFoundExceprion;
import com.clinic.repositories.BodyChangeRepository;
import com.clinic.repositories.OrderRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.ClientService;
import com.clinic.services.OrderService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final ClientService clientService;
    private final OrderRepository orderRepository;

    private final BodyChangeRepository bodyChangeRepository;


    @Autowired
    public OrderServiceImpl(
            ClientService cs,
            OrderRepository or,
            BodyChangeRepository bcr
    ){
        this.clientService = cs;
        this.orderRepository = or;
        this.bodyChangeRepository = bcr;
    }

    @Override
    public Order save(Order order) {
        order = orderRepository.save(order);
        orderRepository.flush();
        return order;
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersByClientId(Long id)
            throws ClientNotFoundException
    {
        Client client = clientService.getClientByPassport(id);
        return orderRepository.findAllByClient(client);
    }

    @Override
    public Order getOrderById(Long id)
            throws OrderNotFoundExceprion
    {
        return orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundExceprion("No order found with id: "+ id));
    }

    @Override
    public AccompanimentScript getScriptByOrderId(long id)
            throws OrderNotFoundExceprion
    {
        return orderRepository
                .findById(id)
                .orElseThrow(()->new OrderNotFoundExceprion("No order found with id: "+ id))
                .getAccompanimentScript();
    }

    @Transactional
    @Override
    public Order createBodyChanges(SimpleBodyChangesUpdate changesData)
            throws OrderNotFoundExceprion
    {
        Order order = orderRepository.findById(changesData.getOrderId())
                .orElseThrow(() -> new OrderNotFoundExceprion("No order found with id: "+ changesData.getOrderId()));

        List<BodyChange> bodyChanges = new ArrayList<>();
        for (String change : changesData.getChanges())
        {
            BodyChange bodyChange = new BodyChange();
            bodyChange.setOrder(order);
            bodyChange.setChange(change);
            bodyChanges.add(bodyChange);
        }

        for (BodyChange bodyChange : bodyChanges)
            System.out.println(bodyChange.getOrder().getId());

        bodyChangeRepository.saveAll(bodyChanges);

        return order;
    }
}
