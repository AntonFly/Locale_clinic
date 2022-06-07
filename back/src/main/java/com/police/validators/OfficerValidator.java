package com.police.validators;

import com.police.entities.Officer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OfficerValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return Officer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Officer officer = (Officer) o;
        ValidationUtils.rejectIfEmpty(errors, "id", "id.empty");

    }
}
