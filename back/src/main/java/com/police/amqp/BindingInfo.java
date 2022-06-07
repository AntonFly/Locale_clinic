package com.police.amqp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;

public class BindingInfo {

    @Getter
    @Setter
    private Queue queue;

    @Getter
    @Setter
    private Binding binding;


}