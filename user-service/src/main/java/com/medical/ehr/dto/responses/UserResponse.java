package com.medical.ehr.dto.responses;

import com.medical.ehr.enums.UserRole;
import lombok.Builder;

import java.util.Date;

@Builder
public record UserResponse(
        String firstname,
        String lastname,
        String username,
        String email,
        String phone,
        UserRole role,
        Date createdAt,
        Date updatedAt
) {
}
