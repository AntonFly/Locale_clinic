package com.police.impl;

import com.police.entities.Call;
import com.police.entities.Officer;
import com.police.entities.User;
import com.police.openam.SimpleUserRegistration;
import com.police.services.CallService;
import com.police.services.EmailService;
import com.police.services.OfficerService;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender sender;
    private OfficerService officerService;
    private SimpleMailMessage callMessage;
    private SimpleMailMessage registrationMessage;

    @Autowired
    public EmailServiceImpl(JavaMailSender jms, OfficerService os, @Qualifier("callMessage") SimpleMailMessage cm, @Qualifier("registrationMessage") SimpleMailMessage rm){
        this.sender = jms;
        this.officerService = os;
        this.callMessage = cm;
        this.registrationMessage = rm;
    }

    @Override
    public void sendCallMessageToOfficer(long officerId, Call call) {

        Officer officer = officerService.getOfficerById(officerId);
        String name = officer.getPerson().getName();
        String surname = officer.getPerson().getSurname();
        String info = call.getDescription();
        String districtName = call.getCallLocation().getDistrict().getName();
        String street = call.getCallLocation().getStreet();
        long houseNumber = call.getCallLocation().getHouseNumber();

        String text = String.format(callMessage.getText(),
                name, surname, districtName, street, houseNumber, info);

        SimpleMailMessage message = new SimpleMailMessage();
        callMessage.copyTo(message);
        message.setTo(officer.getMail());
        message.setText(text);
        sender.send(message);
    }

    @Override
    public void sendRegistrationMessageToOfficer(long officerId, SimpleUserRegistration userData) {

        Officer officer = officerService.getOfficerById(officerId);
        String name = officer.getPerson().getName();
        String surname = officer.getPerson().getSurname();

        String text = String.format(registrationMessage.getText(),
                name, surname, userData.getUsername(), userData.getUserPassword());

        SimpleMailMessage message = new SimpleMailMessage();
        registrationMessage.copyTo(message);
        message.setTo(officer.getMail());
        message.setText(text);
        sender.send(message);
    }


}
