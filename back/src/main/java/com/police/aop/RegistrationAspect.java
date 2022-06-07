package com.police.aop;

import com.police.openam.SimpleOfficerRegistration;
import com.police.openam.SimpleUserRegistration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class RegistrationAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.police.services.UserService.registerUser(..))")
    public void registerUser(){};

    @Before("registerUser()")
    public void registrationUserAttempt(JoinPoint joinPoint){
        SimpleUserRegistration simpleUserRegistration = (SimpleUserRegistration) joinPoint.getArgs()[0];
        String fakePassword = "";
        for (int i = 0; i < simpleUserRegistration.getUserPassword().length(); i++)
            fakePassword += '*';
        logger.info("Attempt to register user with username: " + simpleUserRegistration.getUsername() +
                " and password " + fakePassword + " .");
    }

    @AfterReturning(pointcut = "registerUser()", returning = "userData")
    public void registrationUserResult(JoinPoint joinPoint, SimpleUserRegistration userData){

        if (userData == null)
            logger.info("Registration failed");
        else
            logger.info("User's registration was successful (Username: " + userData.getUsername() + " ).");

    }

    @Pointcut("execution( * com.police.services.AdminService.createOfficerUser(..))")
    public void registerOfficer(){}

    @Before("registerOfficer()")
    public void registrationOfficerAttempt(JoinPoint joinPoint){
        SimpleOfficerRegistration simpleOfficerRegistration = (SimpleOfficerRegistration) joinPoint.getArgs()[0];
        logger.info("Attempt to register officer, corresponding person's passport number is: " + simpleOfficerRegistration.getPassportNumber());
    }

    @AfterReturning(pointcut = "registerOfficer()", returning = "userData")
    public void registrationOfficerResult(SimpleUserRegistration userData){
        if (userData == null)
            logger.info("Registration failed");
        else
            logger.info("New officer was born to vanish the shadows from this city. His username is: " + userData.getUsername());
    }

}
