package com.police.services;

import com.police.entities.Location;
import com.police.entities.Person;
import sun.rmi.server.LoaderHandler;

public interface LocationService {

    Location getLocationByHouseAndStreet(long houseNumber, String Street);
}
