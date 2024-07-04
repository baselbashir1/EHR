package com.medical.ehr.dto.requests;

import com.medical.ehr.enums.UserRole;
import jakarta.validation.constraints.Email;

public record EditUserRequest(
        String firstname,
        String lastname,
        String username,
        @Email(message = "Email is not valid.")
        String email,
        String phone,
        String password,
        UserRole role
) {
}