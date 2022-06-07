package com.police.impl;

import com.police.entities.Call;
import com.police.entities.Crime;
import com.police.entities.Location;
import com.police.entities.Officer;
import com.police.entities.enums.CallStatus;
import com.police.entities.enums.CrimeType;
import com.police.repositories.CallRepository;
import com.police.repositories.OfficerRepository;
import com.police.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CallServiceImpl implements CallService {

    private CallRepository callRepository;
    private OfficerRepository officerRepository;
    private OfficerService officerService;
    private CrimeService crimeService;
    private EmailService emailService;
    private LocationService locationService;
    private AmqpService amqpService;

    @Autowired
    public CallServiceImpl(CallRepository cr, OfficerRepository or, OfficerService os, CrimeService cs, EmailService es, LocationService ls, AmqpService as) {
        this.callRepository = cr;
        this.officerRepository = or;
        this.officerService = os;
        this.crimeService = cs;
        this.emailService = es;
        this.locationService = ls;
        this.amqpService = as;
    }

    @Override
    public Call createCall(String description, Location callLocation, Long dispatcherId, CrimeType crimeType) {

        Call call = new Call();
        call.setDescription(description);
        Location location = locationService.getLocationByHouseAndStreet(callLocation.getHouseNumber(), callLocation.getStreet());
        if (location == null)
            return null;
        call.setCallLocation(location);
        Officer dispatcher = officerRepository.getOne(dispatcherId);
        call.setDispatcher(dispatcher);
        call.setStatus(CallStatus.ACTIVE);
        call.setTime(Timestamp.valueOf(LocalDateTime.now()));
        callRepository.save(call);
        Crime crime = crimeService.createCrimeByCall(call, crimeType);
        List<Officer> squad = officerService.getSquadForCall(dispatcher.getPoliceStation(), crime);
        squad.forEach(a -> {
            addOfficerToCall(a.getId(), call.getId());
            emailService.sendCallMessageToOfficer(a.getId(), call);
            amqpService.sendMessageToOfficer("Call", a.getId());
        });
        return call;

    }

    @Override
    public List<Call> getCallsByOfficer(Officer officer) {
        return callRepository.findAllByCalledOfficersIn(officer);
    }

    @Override
    public void addOfficerToCall(Long officerId, Long callId) {
        Officer officer = officerRepository.getOne(officerId);
        Call call = callRepository.getOne(callId);

        officer.getParticipatedCalls().add(call);
        call.getCalledOfficers().add(officer);

        callRepository.save(call);
    }

    @Override
    public void finishCall(Long callId) {
        Call call = callRepository.getOne(callId);

        call.setStatus(CallStatus.FINISHED);

        callRepository.save(call);
    }

    @Override
    public List<Call> getActiveCallByOfficer(Officer officer) {
        return callRepository.findAllByCalledOfficersInAndStatus(officer, CallStatus.ACTIVE);

    }

    @Override
    public List<Call> getActiveCallByLocation(Location location) {
        return callRepository.findAllByStatusAndCallLocation(CallStatus.ACTIVE, location);
    }
}
