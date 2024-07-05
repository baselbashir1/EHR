package com.medical.ehr.mappers;

import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.RegisterResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import com.medical.ehr.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthMapper {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse mapToRegisterResponse(User user, String token) {
        return RegisterResponse.builder()
                .token(token)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User mapToUser(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .role(UserRole.USER)
                .build();
        return userService.saveUser(user);
    }

}
