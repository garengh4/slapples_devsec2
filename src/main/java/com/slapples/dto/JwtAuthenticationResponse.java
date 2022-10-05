package com.slapples.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private boolean isAuthenticated;
    private UserInfo userInfo;

}
