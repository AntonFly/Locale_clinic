package com.police.impl;

import com.police.entities.District;
import com.police.repositories.DistrictRepository;
import com.police.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService{

    private DistrictRepository districtRepository;

    @Autowired
    public MapServiceImpl(DistrictRepository dr){
        this.districtRepository = dr;
    }

    @Override
    public List<District> getAllDistricts() {

        return districtRepository.findAll();

    }
}
