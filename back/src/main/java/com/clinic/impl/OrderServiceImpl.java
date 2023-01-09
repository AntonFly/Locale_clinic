package com.clinic.impl;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.BodyChangeNotFoundException;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.OrderNotFoundException;
import com.clinic.exceptions.ScenarioNotFoundException;
import com.clinic.repositories.AccompanimentScriptRepository;
import com.clinic.repositories.BodyChangeRepository;
import com.clinic.repositories.OrderRepository;
import com.clinic.services.ClientService;
import com.clinic.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ClientService clientService;
    private final OrderRepository orderRepository;

    private final BodyChangeRepository bodyChangeRepository;

    private final AccompanimentScriptRepository accompanimentScriptRepository;


    @Autowired
    public OrderServiceImpl(
            ClientService cs,
            OrderRepository or,
            BodyChangeRepository bcr,
            AccompanimentScriptRepository asr
    ){
        this.clientService = cs;
        this.orderRepository = or;
        this.bodyChangeRepository = bcr;
        this.accompanimentScriptRepository = asr;
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
            throws OrderNotFoundException
    {
        return orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException("No order found with id: "+ id));
    }

    @Override
    public AccompanimentScript getScriptByOrderId(long id)
            throws OrderNotFoundException, ScenarioNotFoundException {
        Order currentOrder =  orderRepository
                .findById(id)
                .orElseThrow(()->new OrderNotFoundException("No order found with id: "+ id));

        AccompanimentScript script = currentOrder.getAccompanimentScript();

        return  script != null ? script : generateAccompanimentScript(currentOrder);
    }

    @Transactional
    @Override
    public Order createBodyChanges(SimpleBodyChangesUpdate changesData)
            throws OrderNotFoundException
    {
        Order order = orderRepository.findById(changesData.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("No order found with id: "+ changesData.getOrderId()));

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

    @Override
    public List<BodyChange> getBodyChanges(long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Не было найдено заказа с id: "+orderId));
        return order.getBodyChanges();
    }

    @Override
    public Boolean dropBodyChange(long bodyChangeId) throws BodyChangeNotFoundException {
           bodyChangeRepository.deleteById(bodyChangeId);
           return true;

    }

    private AccompanimentScript generateAccompanimentScript(Order order) throws ScenarioNotFoundException {

        Scenario currentScenario = order.getScenario();
        if (currentScenario == null)
            throw new ScenarioNotFoundException("Не было найдено сценариев для заказа с id: "+ order.getId());



        StringBuilder strScript = new StringBuilder();
        List<ModificationScenario> modificationList = currentScenario.getModificationScenarios().stream().sorted(new Comparator<ModificationScenario>() {
            @Override
            public int compare(ModificationScenario o1, ModificationScenario o2) {
                return Long.compare(o2.getPriority(), o1.getPriority());
            }
        }).collect(Collectors.toList());

        for ( ModificationScenario item: modificationList)
             {
                 strScript.append(item.getModification().getAccompaniment()).append("\r\n");
             }

        AccompanimentScript accompanimentScript = new AccompanimentScript();
        accompanimentScript.setScenarios(strScript.toString());

        accompanimentScript = accompanimentScriptRepository.save(accompanimentScript);

        order.setAccompanimentScript(accompanimentScript);

        order = orderRepository.save(order);






        return order.getAccompanimentScript();
    }
}
