package com.slapples.dto;

import lombok.Value;

@Value
public class SignUpResponse {
    private boolean using2FA;
    private String qrCodeImage;
}
