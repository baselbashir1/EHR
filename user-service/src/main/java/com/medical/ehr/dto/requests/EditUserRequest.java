package com.medical.ehr.dto.requests;

import com.medical.ehr.enums.UserRole;
import com.medical.ehr.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record EditUserRequest(
        String firstname,
        String lastname,
        String username,
        @Email(message = "Email is not valid.")
        String email,
        String phone,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain at least one letter and one number.")
        String password,
        UserRole role,
        UserStatus status
) {
}