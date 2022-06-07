package com.police.validators;

import com.police.entities.User;
import com.police.openam.SimpleUserRegistration;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return SimpleUserRegistration.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty");

    }
}
