package com.joi.backendProject.service;

import com.joi.backendProject.application.AppUser;
import com.joi.backendProject.repository.AppUserRepository;
import com.joi.backendProject.utils.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {
    private final int EXPIRING_TIME = 15;
    private final String USER_NOT_FOUND_MSG = "User with email %s was not found";
    @Autowired
    private AppUserRepository repository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String singUp(AppUser user) {
        boolean userExists = repository.
                findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already registered");
        }

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        repository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(EXPIRING_TIME),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: Send confirmation email

        return "User registered succesfully " + token;
    }


    public void enableAppUser(String email) {

    }
}
