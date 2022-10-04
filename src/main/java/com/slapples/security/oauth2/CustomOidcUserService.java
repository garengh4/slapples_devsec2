package com.slapples.security.oauth2;

import com.slapples.exception.OAuth2AuthenticationProcessingException;
import com.slapples.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;



// TODO: who is using this service?
@Service
public class CustomOidcUserService extends OidcUserService {
    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(request);
        try {
            return userService.processUserRegistration(request.getClientRegistration().getRegistrationId(), oidcUser.getAttributes(),
                    oidcUser.getIdToken(), oidcUser.getUserInfo());
        } catch(AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationProcessingException(e.getMessage(), e.getCause());
        }
    }
}
