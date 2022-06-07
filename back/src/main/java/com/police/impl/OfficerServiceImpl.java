package com.police.impl;

import com.police.entities.*;
import com.police.entities.enums.OfficerStatus;
import com.police.entities.enums.Rank;
import com.police.entities.enums.Shift;
import com.police.repositories.OfficerRepository;
import com.police.services.OfficerService;
import com.police.services.PeopleArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OfficerServiceImpl implements OfficerService{

    private OfficerRepository officerRepository;
    private PeopleArchiveService peopleArchiveService;

    @Autowired
    public OfficerServiceImpl(OfficerRepository or, PeopleArchiveService pas){
        this.officerRepository = or;
        this.peopleArchiveService = pas;
    }

    @Override
    public Officer getOfficerById(long id) {
        return officerRepository.findById(id);
    }

    @Override
    public Officer getOfficerByUser(User user) {
        return officerRepository.findByUsr(user);
    }

    @Override
    public void changeStatus(Officer officer, OfficerStatus status) {
        officer.setOfficerStatus(status);
        officerRepository.save(officer);
    }

    @Override
    public List<Officer> getOfficersByCall(Call call) {
        return officerRepository.findAllByParticipatedCallsIn(call);
    }

    @Override
    public List<Officer> getSquadForCall(PoliceStation policeStation, Crime crime) {
        List<Officer> squad = new ArrayList<>();
        int severity = crime.getType().getRisk() * 3;

        for (int i = Rank.CAPTAIN.getSkill(); i >= Rank.OFFICER.getSkill() && severity > 0; i--){

            if (i == Rank.ADMIN.getSkill() || i == Rank.DETECTIVE.getSkill() || i == Rank.DISPATCHER.getSkill())
                continue;

            List<Officer> members = getWaitingOfficersByPoliceStationAndRank(policeStation, Rank.valueOf(i-1));
            Iterator<Officer> iterator = members.iterator();
            while (iterator.hasNext() && severity > i) {
                squad.add(iterator.next());
                severity -= i;
            }
        }
        squad.forEach(a->{
            changeStatus(a, OfficerStatus.CALLED);
            officerRepository.save(a);
        });

        return squad;
    }

    @Override
    public Officer save(Officer officer) {
        officer = officerRepository.save(officer);
        officerRepository.flush();
        return officer;
    }

    @Override
    public void delete(Officer officer) {
        officerRepository.delete(officer);
    }

    @Override
    public List<Officer> getWaitingOfficersByPoliceStationAndRank(PoliceStation policeStation, Rank rank) {
        return officerRepository.findAllByPoliceStationAndRankAndOfficerStatus(policeStation, rank, OfficerStatus.WAITING);
    }


    @Override
    public void prepareDayShift() {
        officerRepository.findAll().forEach(a->{
            if (a.getShift() == Shift.DAY)
                a.setOfficerStatus(OfficerStatus.WAITING);
            else
                a.setOfficerStatus(OfficerStatus.NOT_AT_WORK);
            officerRepository.save(a);
        });
        officerRepository.flush();
    }

    @Override
    public void prepareNightShift() {
        officerRepository.findAll().forEach(a->{
            if (a.getShift() == Shift.NIGHT)
                a.setOfficerStatus(OfficerStatus.WAITING);
            else
                a.setOfficerStatus(OfficerStatus.NOT_AT_WORK);
            officerRepository.save(a);
        });
        officerRepository.flush();
    }

    @Override
    public Officer getOfficerByPassportNumber(long passportNumber) {
        Person person = peopleArchiveService.getPersonByPassportNumber(passportNumber);
        return officerRepository.findByPerson(person);
    }
}
