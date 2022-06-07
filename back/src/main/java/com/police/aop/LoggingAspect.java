package com.police.aop;

import com.police.openam.OpenAmAuthToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());
    @Pointcut("execution( * com.police.openam.OpenAmAuthProvider.authenticate(..))")
    public void login(){}

    @Before("login()")
    public void logAttempt(JoinPoint joinPoint){
        Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        logger.info("Somebody named "+ authentication.getPrincipal() + " is trying to authenticate...");
    }

    @AfterReturning(pointcut = "login()", returning = "token")
    public void logSuccessAttempt(JoinPoint joinPoint, OpenAmAuthToken token){
        logger.info("User with username " + token.getPrincipal() + " and authorities "
                + token.getAuthorities().toString() + " got his pass-token");
    }
}
