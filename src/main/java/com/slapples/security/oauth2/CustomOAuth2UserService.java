package com.slapples.security.oauth2;

import com.slapples.dto.SocialProvider;
import com.slapples.exception.OAuth2AuthenticationProcessingException;
import com.slapples.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try{
            Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
            String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
            if(provider.equals(SocialProvider.LINKEDIN.getProviderType())){
                populateEmailAddressFromLinkedin(oAuth2UserRequest, attributes);
            }
            return userService.processUserRegistration(provider, attributes, null, null);
        } catch(AuthenticationException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationProcessingException(e.getMessage(), e.getCause());
        }
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public void populateEmailAddressFromLinkedin(OAuth2UserRequest oAuth2UserRequest, Map<String, Object> attributes) {
        String emailEndpointUri = environment.getProperty("linkedin.email-address-uri");
        Assert.notNull(emailEndpointUri, "Linkedin emailId endpoint required");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> entity = new HttpEntity<>("", headers);
        ResponseEntity<Map> response = restTemplate.exchange(emailEndpointUri, HttpMethod.GET, entity, Map.class);
        List<?> list = (List<?>) response.getBody().get("elements");
        Map map = (Map<?, ?>) ((Map<?, ?>) list.get(0)).get("handle~");
        attributes.putAll(map);
    }
}
