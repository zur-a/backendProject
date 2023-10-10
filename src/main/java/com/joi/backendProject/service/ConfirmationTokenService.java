package com.joi.backendProject.service;

import com.joi.backendProject.repository.ConfirmationTokenRepository;
import com.joi.backendProject.utils.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository repository;

    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }
}
