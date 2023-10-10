package com.joi.backendProject.repository;

import com.joi.backendProject.utils.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    //TODO: Add database query as annotation
    Optional<ConfirmationToken> findByToken(String token);
}
