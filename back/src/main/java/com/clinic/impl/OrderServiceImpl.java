package com.clinic.impl;

import com.clinic.entities.Order;
import com.clinic.entities.User;
import com.clinic.repositories.OrderRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.OrderService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(
            OrderRepository or
    ){
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

}
