package com.slapples.service;

import com.slapples.dto.SignupRequest;
import com.slapples.exception.UserAlreadyExistAuthenticationException;
import com.slapples.model.Role;
import com.slapples.model.User;
import com.slapples.repo.RoleRepository;
import com.slapples.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(value="transactionManager")
    public User registerNewUser(SignupRequest signupRequest) throws UserAlreadyExistAuthenticationException {
        if(signupRequest.getUserId() != null && userRepository.existsById(signupRequest.getUserId())) {
            throw new UserAlreadyExistAuthenticationException("User with userId " + signupRequest.getUserId() + " already exist");
        } else if (userRepository.existsByEmail(signupRequest.getEmail())){
            throw new UserAlreadyExistAuthenticationException("user with emailId " + signupRequest.getEmail() + " already exist.");
        } else {
            User user = buildUser(signupRequest);
            Date now = Calendar.getInstance().getTime();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
            userRepository.flush();
            return user;
        }
    }
    private User buildUser(SignupRequest formDTO) {
        User user = new User();
        user.setDisplayName(formDTO.getDisplayName());
        user.setEmail(formDTO.getEmail());
        user.setPassword(formDTO.getPassword()); //TODO: encode password here
        final HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setEnabled(true);
        user.setProviderUserId(formDTO.getProvidedUserId());
        return user;
    }
}
