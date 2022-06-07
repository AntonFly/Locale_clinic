package com.police.services;

import com.police.entities.*;
import com.police.entities.enums.OfficerStatus;
import com.police.entities.enums.Rank;

import java.util.List;

public interface OfficerService {

    Officer save(Officer officer);

    Officer getOfficerById(long id);

    Officer getOfficerByUser(User user);

    List<Officer> getWaitingOfficersByPoliceStationAndRank(PoliceStation policeStation, Rank rank);

    List<Officer> getOfficersByCall(Call call);

    void changeStatus(Officer officer, OfficerStatus status);

    List<Officer> getSquadForCall(PoliceStation policeStation, Crime crime);

    void delete(Officer officer);

    void prepareDayShift();

    void prepareNightShift();

    Officer getOfficerByPassportNumber(long passportNumber);
}
