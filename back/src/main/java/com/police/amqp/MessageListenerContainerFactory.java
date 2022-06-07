package com.police.amqp;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerContainerFactory {

    private ConnectionFactory connectionFactory;

    @Autowired
    public MessageListenerContainerFactory(ConnectionFactory cf){
        this.connectionFactory = cf;
    }

    public MessageListenerContainerFactory(){}

    public MessageListenerContainer createMessageListenerContainer(String qName){
        SimpleMessageListenerContainer messageListenerContainer =
                new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.addQueueNames(qName);
        return messageListenerContainer;
    }

}
