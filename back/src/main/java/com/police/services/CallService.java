package com.police.services;

import com.police.entities.Call;
import com.police.entities.Location;
import com.police.entities.Officer;
import com.police.entities.enums.CrimeType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CallService {

    @Transactional
    public Call createCall(String description, Location callLocation, Long dispatcherId, CrimeType crimeType);

    List<Call> getCallsByOfficer(Officer officer);

    void addOfficerToCall(Long officerId, Long callId);

    void finishCall(Long callId);

    List<Call> getActiveCallByOfficer(Officer officer);

    List<Call> getActiveCallByLocation(Location location);

}
