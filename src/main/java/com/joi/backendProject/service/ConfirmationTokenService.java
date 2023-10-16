package com.joi.backendProject.service;

import com.joi.backendProject.repository.ConfirmationTokenRepository;
import com.joi.backendProject.utils.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository repository;

    @Autowired
    private ConfirmationToken confirmationToken;

    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        Optional<ConfirmationToken> optionalToken = repository.findByToken(token);
        if (optionalToken.isPresent()) {
            ConfirmationToken confirmationToken = optionalToken.get();
            confirmationToken.setConfirmedTime(LocalDateTime.now());          // Set the confirmedTime to the current time
            repository.save(confirmationToken);                              // Save the updated entity
        } else {
            throw new IllegalStateException("Token not found");
        }
    }
}
