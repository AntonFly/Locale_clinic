package com.police.impl;

import com.police.entities.Officer;
import com.police.entities.Person;
import com.police.entities.PoliceStation;
import com.police.entities.User;
import com.police.entities.enums.OfficerStatus;
import com.police.openam.SimpleOfficerRegistration;
import com.police.openam.SimpleUserRegistration;
import com.police.services.*;
import com.police.validators.OfficerValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private PeopleArchiveService peopleArchiveService;
    private UserService userService;
    private OfficerService officerService;
    private PoliceStationInfoService policeStationInfoService;
    private EmailService emailService;
    private OfficerValidator officerValidator;

    @Autowired
    public AdminServiceImpl(PeopleArchiveService pas, UserService us, OfficerService os, PoliceStationInfoService psis, EmailService es, OfficerValidator ov) {
        this.peopleArchiveService = pas;
        this.userService = us;
        this.officerService = os;
        this.policeStationInfoService = psis;
        this.emailService = es;
        this.officerValidator = ov;
    }

    @Override
    public SimpleUserRegistration createOfficerUser(SimpleOfficerRegistration officerData) {
        Officer officer = new Officer();
        Person person = peopleArchiveService.getPersonByPassportNumber(officerData.getPassportNumber());
        officer.setPerson(person);
        if (officerService.getOfficerByPassportNumber(officerData.getPassportNumber()) != null)
            return null;
        PoliceStation policeStation = policeStationInfoService.getPoliceStationById(officerData.getStationId());
        officer.setPoliceStation(policeStation);
        officer.setRank(officerData.getRank());
        officer.setJabber(officerData.getJabber());
        officer.setMail(officerData.getMail());
        officer.setShift(officerData.getShift());
        officer.setOfficerStatus(OfficerStatus.UNKNOWN);
        officer = officerService.save(officer);
        SimpleUserRegistration userData = new SimpleUserRegistration();
        userData.setUsername(Long.toString(officer.getId()));
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String pwd = RandomStringUtils.random( 8, characters );
        userData.setUserPassword(pwd);
        userData = userService.registerUser(userData);
        if (userData == null){
            return null;
        }
        User user = userService.getUserByUsername(userData.getUsername());
        officer.setSalary(officer.getRank().getSkill()*100 + 100L);
        officer.setPremium(10L);
        switch (officer.getRank()){
            case DISPATCHER:
                userService.setRole(user, "DISPATCHER");
                break;
            case ADMIN:
                userService.setRole(user, "ADMIN");
                break;
            default:
                userService.setRole(user,"OFFICER" );
        }
        user.setOfficer(officer);
        officer.setUsr(user);
        userService.save(user);
        officerService.save(officer);
        emailService.sendRegistrationMessageToOfficer(officer.getId(), userData);
        return userData;

    }

    @Override
    public User changeUserData(User user) {

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
