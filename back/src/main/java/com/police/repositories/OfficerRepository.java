package com.police.repositories;

import com.police.entities.*;
import com.police.entities.enums.OfficerStatus;
import com.police.entities.enums.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

    Officer findById(long id);

    Officer findByUsr(User user);

    List<Officer> findAllByParticipatedCallsIn(Call call);

    List<Officer> findAllByPoliceStationAndRankAndOfficerStatus(PoliceStation policeStation, Rank rank, OfficerStatus status);

    Officer findByPerson(Person person);
}
