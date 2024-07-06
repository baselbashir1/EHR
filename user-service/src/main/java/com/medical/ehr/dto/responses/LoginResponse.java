package com.medical.ehr.dto.responses;

import com.medical.ehr.enums.UserRole;
import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String firstname,
        String lastname,
        String username,
        String email,
        String phone,
        UserRole role
) {
}
