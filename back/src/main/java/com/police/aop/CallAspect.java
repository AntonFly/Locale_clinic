package com.police.aop;

import com.police.entities.Call;
import com.police.entities.Location;
import com.police.entities.enums.CrimeType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class CallAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.police.services.CallService.createCall(..))")
    public void callCreation(){}

    @Before("callCreation()")
    public void callCreationAttempt(JoinPoint joinPoint){
        CrimeType crimeType = (CrimeType) joinPoint.getArgs()[3];
        Location location = (Location) joinPoint.getArgs()[1];
        logger.info("Seems like there's " + crimeType + " going on near " + location.getStreet() + ", "
        + location.getHouseNumber() + ".");
    }

    @AfterReturning(pointcut = "callCreation()", returning = "call")
    public void callCreationResult(JoinPoint joinPoint, Call call){
        if (call == null)
            logger.info("No, just rumors.");
        else
            logger.info("Officers (" + call.getCalledOfficers().size() + ") were sent to " + call.getCallLocation().getStreet() + ", " +
            call.getCallLocation().getHouseNumber() + ", " );
    }

}
