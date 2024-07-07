package com.medical.ehr.dto.responses;

import com.medical.ehr.enums.UserRole;
import lombok.Builder;

@Builder
public record UserRoleResponse(
        Long userId,
        UserRole role
) {
}
