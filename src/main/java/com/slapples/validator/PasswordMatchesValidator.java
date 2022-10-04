package com.slapples.validator;

import com.slapples.dto.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {
    @Override
    public boolean isValid(SignUpRequest user, ConstraintValidatorContext context) {
        return user.getPassword().equals((user.getMatchingPassword()));
    }
}
