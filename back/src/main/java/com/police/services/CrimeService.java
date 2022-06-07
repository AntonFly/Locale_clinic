package com.police.services;

import com.police.entities.Call;
import com.police.entities.Crime;
import com.police.entities.Location;
import com.police.entities.enums.CrimeType;

import java.util.List;

public interface CrimeService {

    List<Crime> getCrimesByPerson(Long passportNumber);

    List<Crime> getCrimesByType(CrimeType crimeType);

    Crime createCrimeByCall(Call call, CrimeType type);

    List<Crime> getCrimesByLocation(Location location);

    List<Crime> getAllCrimes();
}
