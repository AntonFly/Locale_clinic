package com.police.configs.email;

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
        mailMessage.setText("Officer %s %s, you received a call.\n" +
                "Follow the standard Call receiving protocol \n" +
                "Information:\n" +
                "Location: %s district, %s street house number %d\n" +
                "Description: %s\n");
        return mailMessage;
    }

    @Bean(name = "registrationMessage")
    public SimpleMailMessage registrationMessageTemplate(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("nypolicecw@gmail.com");
        mailMessage.setSubject("Welcome to NYPD");
        mailMessage.setSentDate(new Date());
        mailMessage.setText("%s %s, you were successfully admitted to employment as an NYPD officer.\n" +
                "Here is information required for login at NYPD site \n" +
                "Login: %s\n" +
                "Password: %s\n" +
                "---------------------\n" +
                "With gratitude, \n NYPD");
        return mailMessage;
    }

}
