package com.medical.ehr.utils;

import com.medical.ehr.dto.responses.UserRoleResponse;
import com.medical.ehr.models.User;
import com.medical.ehr.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLayer {

    private final UserService userService;

    public UserRoleResponse getUserIdAndRoleFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.loadUserByUsername(name);
        return new UserRoleResponse(user.getId(), user.getRole());
    }

}
