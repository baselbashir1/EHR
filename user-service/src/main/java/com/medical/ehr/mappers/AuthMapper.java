package com.medical.ehr.mappers;

import com.medical.ehr.dto.responses.RegisterResponse;
import com.medical.ehr.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthMapper {

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

}
