package com.police.impl;

import com.police.amqp.BindingInfo;
import com.police.amqp.Destination;
import com.police.amqp.MessageListenerContainerFactory;
import com.police.services.AmqpService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Map;

@Service
public class AmqpServiceImpl implements AmqpService {

    private MessageListenerContainerFactory messageListenerContainerFactory;

    private RabbitTemplate rabbitTemplate;

    private Destination destinations;

    private AmqpAdmin amqpAdmin;

    private DirectExchange exchange;

    @Autowired
    public AmqpServiceImpl(MessageListenerContainerFactory mlcf, RabbitTemplate rt,
                           Destination d, AmqpAdmin aa){
        this.messageListenerContainerFactory = mlcf;
        this.rabbitTemplate = rt;
        this.destinations = d;
        this.amqpAdmin = aa;
    }

    @PostConstruct
    public void config(){
        exchange = new DirectExchange("police");
        amqpAdmin.declareExchange(exchange);
    }

    @Override
    public void sendMessageToOfficer(String message, long officerId) {

        Queue queue = new Queue(Long.toString(officerId), true, true, true);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(Long.toString(officerId));
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        BindingInfo info = new BindingInfo();
        info.setBinding(binding);
        info.setQueue(queue);

        destinations.getDestinations()
                .put(officerId, info);

        rabbitTemplate.convertAndSend(exchange.getName(), Long.toString(officerId), message);

    }

    @Override
    public Flux<?>  receiveMessageForOfficer(long officerId) {

        BindingInfo bindingInfo = destinations.getDestinations().get(officerId);

        if (bindingInfo == null)
            return Flux.just(ResponseEntity.notFound().build());

        MessageListenerContainer mlc = messageListenerContainerFactory
                .createMessageListenerContainer(Long.toString(officerId));

        Flux<String> f = Flux.create(emitter -> {
            mlc.setupMessageListener( m -> {
                String message = new String((m.getBody()));
                emitter.next(message);
            });
            emitter.onRequest(v -> {
                mlc.start();
            });
            emitter.onDispose(() ->{
                Map<Long, BindingInfo> previousDestinations = destinations.getDestinations();
                previousDestinations.remove(officerId);
                destinations.setDestinations(previousDestinations);
                mlc.stop();
            });
        });

        return Flux.interval(Duration.ofSeconds(3))
                .map(v -> "No info")
                .mergeWith(f);
    }


}
