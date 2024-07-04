package com.medical.ehr.dto.responses;

import com.medical.ehr.enums.UserRole;

public record UserRoleResponse(
        Long userId,
        UserRole role
) {
}
