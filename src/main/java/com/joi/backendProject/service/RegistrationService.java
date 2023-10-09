package com.joi.backendProject.service;

import com.joi.backendProject.request.RegistrationRequest;
import com.joi.backendProject.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator validator;

    public String register(RegistrationRequest request) {
        boolean emailIsValid = validator.test(request.getEmail());

        // TODO: Create a better way to check a valid email
        if (!emailIsValid) {
            throw new IllegalStateException("Email is not valid");
        }
        return "Email has been registered";
    }

}
