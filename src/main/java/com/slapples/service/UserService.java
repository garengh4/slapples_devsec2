package com.slapples.service;

import com.slapples.dto.LocalUser;
import com.slapples.dto.SignUpRequest;
import com.slapples.exception.UserAlreadyExistAuthenticationException;
import com.slapples.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    public User registerNewUser(SignUpRequest signupRequest) throws UserAlreadyExistAuthenticationException;
    User findUserByEmail(String emailId);
    Optional<User> findUserById(Long id);

    @Transactional
    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
