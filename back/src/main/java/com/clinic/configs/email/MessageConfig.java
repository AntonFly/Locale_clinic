package com.clinic.configs.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

@Configuration
public class MessageConfig {

    @Bean(name = "registrationMessage")
    public SimpleMailMessage registrationMessageTemplate(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("inaclinic@outlook.com");
        mailMessage.setSubject("Welcome to INA Clinic");
        mailMessage.setSentDate(new Date());
        mailMessage.setText("%s %s, you have been registered as user for INA Clinic system.\n" +
                "Here is information required for login: \n" +
                "Login: %s\n" +
                "Password: %s\n" +
                "---------------------\n" +
                "With gratitude, \n INA Clinic");
        return mailMessage;
    }

}
