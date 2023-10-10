package com.joi.backendProject.utils;

import com.joi.backendProject.application.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    @Id
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdTime;
    @Column(nullable = false)
    private LocalDateTime expireTime;
    @Column
    private LocalDateTime confirmedTime;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser user;

    public ConfirmationToken(String token,
                             LocalDateTime createdTime,
                             LocalDateTime expireTime,
                             AppUser user) {
        this.token = token;
        this.createdTime = createdTime;
        this.expireTime = expireTime;
        this.user = user;
    }
}
