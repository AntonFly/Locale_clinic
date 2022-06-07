package com.police.services;

import com.police.entities.Call;
import com.police.openam.SimpleUserRegistration;

public interface EmailService {

    void sendCallMessageToOfficer(long officerId, Call call);

    void sendRegistrationMessageToOfficer(long officerId, SimpleUserRegistration userData);
}
