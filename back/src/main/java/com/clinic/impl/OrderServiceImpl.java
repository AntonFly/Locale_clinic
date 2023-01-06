package com.clinic.impl;

import com.clinic.entities.Client;
import com.clinic.entities.Order;
import com.clinic.entities.User;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.OrderNotFoundExceprion;
import com.clinic.repositories.OrderRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.ClientService;
import com.clinic.services.OrderService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private ClientService clientService;
    private OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(
            ClientService cs,
            OrderRepository or
    ){
        this.clientService = cs;
        this.orderRepository = or;
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
    public Order getOrderById(Long id) throws OrderNotFoundExceprion {
        Optional<Order> order = orderRepository.findById(id);

        return order.orElseThrow(()->new OrderNotFoundExceprion("No order found with id: "+id));
    }


}
