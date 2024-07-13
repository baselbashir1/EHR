package com.medical.ehr.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotNull(message = "Firstname is required.")
        @NotEmpty(message = "Firstname is required.")
        String firstname,
        @NotNull(message = "Lastname is required.")
        @NotEmpty(message = "Lastname is required.")
        String lastname,
        @NotNull(message = "Username is required.")
        @NotEmpty(message = "Username is required.")
        String username,
        @NotNull(message = "Email is required.")
        @NotEmpty(message = "Email is required.")
        @Email(message = "Email is not valid.")
        String email,
        @NotNull(message = "Phone number is required.")
        @NotEmpty(message = "Phone number is required.")
        String phone,
        @NotNull(message = "Password is required.")
        @NotEmpty(message = "Password is required.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain at least one letter and one number.")
        String password
) {
}
