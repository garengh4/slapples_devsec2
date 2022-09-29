package com.slapples.service;

import com.slapples.dto.SignupRequest;
import com.slapples.exception.UserAlreadyExistAuthenticationException;
import com.slapples.model.User;

public interface UserService {
    public User registerNewUser(SignupRequest signupRequest) throws UserAlreadyExistAuthenticationException;

}
