package com.joi.backendProject.controller;

import com.joi.backendProject.request.RegistrationRequest;
import com.joi.backendProject.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
