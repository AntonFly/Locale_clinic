package com.police.impl;

import com.police.entities.PoliceStation;
import com.police.repositories.PoliceStationRepository;
import com.police.services.PoliceStationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoliceStationInfoServiceImpl implements PoliceStationInfoService{

    PoliceStationRepository policeStationRepository;

    @Autowired
    public PoliceStationInfoServiceImpl(PoliceStationRepository psr){
        this.policeStationRepository = psr;
    }

    @Transactional
    @Override
    public PoliceStation getPoliceStationById(Long policeStationId) {
        return policeStationRepository.getOne(policeStationId);
    }

    @Override
    public List<PoliceStation> getAll() {
        return policeStationRepository.findAll();
    }
}
