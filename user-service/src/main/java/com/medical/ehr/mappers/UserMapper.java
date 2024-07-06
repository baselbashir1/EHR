package com.medical.ehr.mappers;

import com.medical.ehr.dto.requests.AddUserRequest;
import com.medical.ehr.dto.requests.EditUserRequest;
import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.models.Doctor;
import com.medical.ehr.models.Secretary;
import com.medical.ehr.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

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

    public User mapToUser(User user) {
        return User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public User mapToUser(AddUserRequest addUserRequest) {
        return User.builder()
                .firstname(addUserRequest.firstname())
                .lastname(addUserRequest.lastname())
                .username(addUserRequest.username())
                .email(addUserRequest.email())
                .password(passwordEncoder.encode(addUserRequest.password()))
                .phone(addUserRequest.phone())
                .role(addUserRequest.role())
                .build();
    }

    public void mapToUser(User user, EditUserRequest editUserRequest) {
        user.setFirstname(editUserRequest.firstname());
        user.setLastname(editUserRequest.lastname());
        user.setUsername(editUserRequest.username());
        user.setEmail(editUserRequest.email());
        user.setPhone(editUserRequest.phone());
        user.setPassword(passwordEncoder.encode(editUserRequest.password()));
        user.setRole(editUserRequest.role());
    }

    public Doctor mapToDoctor(User user, AddUserRequest addUserRequest) {
        return Doctor.builder()
                .specialty(addUserRequest.doctorSpecialty())
                .clinicId(addUserRequest.clinicId())
                .user(user)
                .build();
    }

    public Secretary mapToSecretary(User user, AddUserRequest addUserRequest) {
        return Secretary.builder()
                .doctorId(addUserRequest.doctorId())
                .user(user)
                .build();
    }

}
