package com.joi.backendProject.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public record RegistrationRequest(String firstName, String lastName, String password, String email) {
}
