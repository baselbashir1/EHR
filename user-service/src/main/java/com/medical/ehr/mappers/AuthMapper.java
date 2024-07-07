package com.medical.ehr.mappers;

import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.LoginResponse;
import com.medical.ehr.dto.responses.RegisterResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public RegisterResponse mapToRegisterResponse(User user, String token) {
        return RegisterResponse.builder()
                .token(token)
                .userId(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public LoginResponse mapToLoginResponse(User user, String token) {
        return LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public User mapToUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .role(UserRole.PATIENT)
                .build();
    }

}
