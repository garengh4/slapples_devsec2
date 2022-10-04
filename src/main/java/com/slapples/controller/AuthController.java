package com.slapples.controller;

import com.slapples.dto.SignUpRequest;
import com.slapples.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    UserService userService;

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
