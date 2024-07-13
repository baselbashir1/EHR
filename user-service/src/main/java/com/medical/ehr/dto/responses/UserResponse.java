package com.medical.ehr.dto.responses;

import com.medical.ehr.enums.UserRole;
import com.medical.ehr.enums.UserStatus;
import lombok.Builder;

import java.util.Date;

@Builder
public record UserResponse(
        Long userId,
        String firstname,
        String lastname,
        String username,
        String email,
        String phone,
        UserRole role,
        UserStatus status
) {
}
