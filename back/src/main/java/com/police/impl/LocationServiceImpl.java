package com.police.impl;

import com.police.entities.Location;
import com.police.repositories.LocationRepository;
import com.police.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public  LocationServiceImpl(LocationRepository lr){
        this.locationRepository = lr;
    }

    @Override
    public Location getLocationByHouseAndStreet(long houseNumber, String street) {
        List<Location> locations = locationRepository.findAllByHouseNumberAndStreet(houseNumber, street);
        if (locations != null && locations.size() > 0)
            return locations.get(0);
        return null;
    }


}
