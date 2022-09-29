package com.slapples.validator;

import com.slapples.dto.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupRequest> {
    @Override
    public boolean isValid(SignupRequest user, ConstraintValidatorContext context) {
        return user.getPassword().equals((user.getMatchingPassword()));
    }
}
