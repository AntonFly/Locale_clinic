package com.clinic.configs.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

@Configuration
public class MessageConfig {

    @Bean(name = "callMessage")
    public SimpleMailMessage callMessageTemplate(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("nypolicecw@gmail.com");
        mailMessage.setSubject("Call");
        mailMessage.setSentDate(new Date());
        mailMessage.setText("");
        return mailMessage;
    }

    @Bean(name = "registrationMessage")
    public SimpleMailMessage registrationMessageTemplate(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("nypolicecw@gmail.com");
        mailMessage.setSubject("Welcome to INA Clinic");
        mailMessage.setSentDate(new Date());
        mailMessage.setText("");
        return mailMessage;
    }

}
