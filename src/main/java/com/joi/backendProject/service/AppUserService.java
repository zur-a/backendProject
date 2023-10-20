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

    public String signUp(AppUser user) {
        if (emailAlreadyExists(user.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }

        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        repository.save(user);

        String token = generateConfirmationToken(user);
        return token;
    }

    private boolean emailAlreadyExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private String generateConfirmationToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = createConfirmationToken(token, user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    private ConfirmationToken createConfirmationToken(String token, AppUser user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusMinutes(EXPIRING_TIME);
        return new ConfirmationToken(token, now, expirationTime, user);
    }


    public void enableAppUser(String email) {
        AppUser user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

        user.setEnabled(true);
        repository.save(user);
    }


}
