package com.medical.ehr.dto.requests;

import com.medical.ehr.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AddUserRequest(
        @NotNull(message = "Firstname is required.")
        String firstname,
        @NotNull(message = "Lastname is required.")
        String lastname,
        @NotNull(message = "Username is required.")
        String username,
        @NotNull(message = "Email is required.")
        @Email(message = "Email is not valid.")
        String email,
        @NotNull(message = "Phone number is required.")
        String phone,
        @NotNull(message = "Password is required.")
        String password,
        @NotNull(message = "Role is required.")
        UserRole role,
        Long clinicId,
        String doctorSpecialty,
        Long doctorId
) {
}