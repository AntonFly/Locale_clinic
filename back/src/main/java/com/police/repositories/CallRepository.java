package com.police.repositories;

import com.police.entities.Call;
import com.police.entities.Location;
import com.police.entities.Officer;
import com.police.entities.enums.CallStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {

    List<Call> findAllByCalledOfficersIn(Officer officer);

    List<Call> findAllByCalledOfficersInAndStatus(Officer officer, CallStatus status);

    List<Call> findAllByStatusAndCallLocation(CallStatus callStatus, Location location);

}
