package com.joi.backendProject.service;

import com.joi.backendProject.application.AppUser;
import com.joi.backendProject.application.AppUserRole;
import com.joi.backendProject.request.RegistrationRequest;
import com.joi.backendProject.utils.ConfirmationToken;
import com.joi.backendProject.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joi.backendProject.exceptions.InvalidEmailException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator validator;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    ConfirmationTokenService confirmationTokenService;


    public String register(RegistrationRequest request) {
        try {
            if (!validator.test(request.getEmail())) {
                throw new InvalidEmailException("Email is not valid: " + request.getEmail());
            }
            // Register the email here
            return appUserService.singUp(
                    new AppUser(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getEmail(),
                            request.getPassword(),
                            AppUserRole.USER
                    )
            );
        } catch (InvalidEmailException e) {
            // Handle the email validation error
            // For example, log the error or return an error response
            // You can also rethrow the exception if necessary
            return "Error: " + e.getMessage(); // Or another appropriate response
        }
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                    new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedTime() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expireTime = confirmationToken.getExpireTime();

        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getUser().getEmail());
        return "User has validated his account";
    }
}
