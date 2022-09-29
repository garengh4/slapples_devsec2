package com.slapples.dto;


import com.slapples.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {
    private Long userId;
    private String providedUserId;

    @NotEmpty
    private String displayName;

    @NotEmpty
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 6, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;

    public SignupRequest(String providedUserId, String displayName, String email, SocialProvider socialProvider, String password) {
        this.providedUserId = providedUserId;
        this.displayName = displayName;
        this.email = email;
        this.socialProvider = socialProvider;
        this.password = password;
    }


}
