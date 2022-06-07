package com.police.openam;

import com.police.entities.enums.Rank;
import com.police.entities.enums.Shift;
import lombok.Data;

@Data
public class SimpleOfficerRegistration {

    private String jabber;
    private String mail;
    private Rank rank;
    private Shift shift;
    private long passportNumber;
    private long stationId;

}
