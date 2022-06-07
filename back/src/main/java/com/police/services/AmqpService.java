package com.police.services;

import com.police.entities.Officer;
import reactor.core.publisher.Flux;

public interface AmqpService {

    void sendMessageToOfficer(String message, long officerId);

    Flux<?> receiveMessageForOfficer(long officerId);

}
