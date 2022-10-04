package com.slapples.dto;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private UserInfo user;
}
