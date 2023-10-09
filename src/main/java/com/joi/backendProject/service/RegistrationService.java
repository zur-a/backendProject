package com.joi.backendProject.service;

import com.joi.backendProject.application.AppUser;
import com.joi.backendProject.application.AppUserRole;
import com.joi.backendProject.request.RegistrationRequest;
import com.joi.backendProject.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joi.backendProject.exceptions.InvalidEmailException;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator validator;

    @Autowired
    private AppUserService service;

//    public String register(RegistrationRequest request) {
//        boolean emailIsValid = validator.test(request.getEmail());
//
//        // TODO: Create a better way to check a valid email
//        if (!emailIsValid) {
//            throw new IllegalStateException("Email is not valid");
//        }
//        return "Email has been registered";
//    }

    public String register(RegistrationRequest request) {
        try {
            if (!validator.test(request.getEmail())) {
                throw new InvalidEmailException("Email is not valid: " + request.getEmail());
            }
            // Register the email here
            return service.singUp(
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


}
