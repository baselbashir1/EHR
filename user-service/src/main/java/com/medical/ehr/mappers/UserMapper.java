package com.medical.ehr.mappers;

import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
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
