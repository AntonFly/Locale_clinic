package com.police.services;

import com.police.entities.PoliceStation;

import java.util.List;

public interface PoliceStationInfoService {

    PoliceStation getPoliceStationById(Long policeStationId);

    List<PoliceStation> getAll();

}
