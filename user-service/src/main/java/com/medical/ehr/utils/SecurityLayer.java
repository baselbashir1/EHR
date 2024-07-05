package com.medical.ehr.utils;

import com.medical.ehr.dto.responses.UserRoleResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLayer {

    private final UserRepository userRepository;

    public UserRoleResponse getUserIdAndRoleFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        return new UserRoleResponse(user.getId(), user.getRole());
    }

    public void authorizeAdmin() {
        UserRoleResponse userIdAndRole = getUserIdAndRoleFromToken();
        if (!userIdAndRole.role().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("You don't have permission to add the user.");
        }
    }

}
