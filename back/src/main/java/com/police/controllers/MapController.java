package com.police.controllers;

import com.police.entities.District;
import com.police.entities.PoliceStation;
import com.police.services.MapService;
import com.police.services.PoliceStationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {

    private MapService mapService;

    @Autowired
    public MapController(MapService ms){
        this.mapService = ms;
    }

    @GetMapping
    public List<District> StationInfo(){
        return mapService.getAllDistricts();

    }

}
