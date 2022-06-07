package com.police.aop;

import com.police.entities.Officer;
import com.police.entities.enums.OfficerStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class OfficerAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.police.services.OfficerService.changeStatus(..))")
    public void statusChange() {
    }

    @AfterReturning(pointcut = "statusChange()")
    public void statusChangeResult(JoinPoint joinPoint) {
        Officer officer = (Officer) joinPoint.getArgs()[0];
        OfficerStatus newStatus = (OfficerStatus) joinPoint.getArgs()[1];

        switch (newStatus) {
            case WAITING:
                logger.info("Officer №" + officer.getId() + " is now free and waiting for calls.");
                break;
            case CALLED:
                logger.info("Officer №" + officer.getId() + " is now busy because of a call.");
                break;
        }
    }

    @Pointcut("execution(* com.police.services.OfficerService.prepare*(..))")
    public void shiftChange() {
    }

    @Before("shiftChange()")
    public void shiftChangeAttempt(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().contains("Day"))
            logger.info("Good morning NYPD, get some rest night-shifted officers.");
        else
            logger.info("Good night NYPD, sleep well day-shifted officers.");
    }

    @AfterReturning(pointcut = "shiftChange()")
    public void shiftChangeResult(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().contains("Day"))
            logger.info("Hello, day-shifted officers, now it's your time to shine.");
        else
            logger.info("Hello, night-shifted officers, it's time for The Night Watch.");
    }

}
