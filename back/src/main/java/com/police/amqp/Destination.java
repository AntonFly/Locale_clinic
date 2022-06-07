package com.police.amqp;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class Destination {

    @Getter
    @Setter
    private Map<Long, BindingInfo> destinations = new HashMap<>();

}
