package com.medical.ehr.utils;

import com.medical.ehr.clients.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLayer {

    private final UserServiceClient userServiceClient;

    public String getUserIdAndRoleFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = userServiceClient.findByUsername(username);
        return "userId: " + user.getUsername() + " role: " + user.getPassword();
    }

}
