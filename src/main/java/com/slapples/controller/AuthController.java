package com.slapples.controller;

import com.slapples.dto.JwtAuthenticationResponse;
import com.slapples.dto.LocalUser;
import com.slapples.dto.LoginRequest;
import com.slapples.dto.SignUpRequest;
import com.slapples.security.jwt.TokenProvider;
import com.slapples.service.UserService;
import com.slapples.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    Environment environment;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signupRequest) {
        try {
            userService.registerNewUser(signupRequest);
        } catch(Exception e) {
            log.error("Exception occurred: " + e);
            String badRegisterUserMessage = environment.getProperty("AuthController.SIGNUP_USER_FAIL");
            return new ResponseEntity<>(badRegisterUserMessage, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(environment.getProperty("AuthController.SIGNUP_USER_SUCCESS"));
    }

}
