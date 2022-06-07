package com.police.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class MailAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.police.services.EmailService.send*(..))")
    public void sending(){}

    @Before("sending()")
    public void sendingAttempt(JoinPoint joinPoint){
        long id = (long) joinPoint.getArgs()[0];
        if (joinPoint.getSignature().getName().contains("Call"))
            logger.info("Sending call message to officer №" + String.valueOf(id));
        else
            logger.info("Sending registration message to officer №" + String.valueOf(id));
    }

    @AfterReturning(pointcut = "sending()")
    public void sendingSuccess(JoinPoint joinPoint){
        if (joinPoint.getSignature().getName().contains("Call"))
            logger.info("Call message was successfully sent");
        else
            logger.info("Registration message was successfully sent");

    }

    @AfterThrowing(pointcut = "sending()")
    public void sendingFail(JoinPoint joinPoint){
        if (joinPoint.getSignature().getName().contains("Call"))
            logger.info("Sending call message failed");
        else
            logger.info("Sending registration message failed");
    }
}
