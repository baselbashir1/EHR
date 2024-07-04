package com.medical.ehr.services;

import com.medical.ehr.dto.requests.EditUserRequest;
import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.requests.AddUserRequest;
import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.mappers.UserMapper;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepository.save(
                User.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .build()
        );
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return userMapper.mapToUserResponse(user);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return userMapper.mapToUserResponse(user);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    public void validateUser(String username, String email) {
        if (loadUserByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (loadUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
    }

    public User registerUser(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .role(UserRole.USER)
                .build();
        return saveUser(user);
    }

    public UserResponse addUser(AddUserRequest addUserRequest) {
        validateUser(addUserRequest.username(), addUserRequest.email());
        User user = User.builder()
                .firstname(addUserRequest.firstname())
                .lastname(addUserRequest.lastname())
                .username(addUserRequest.username())
                .email(addUserRequest.email())
                .password(passwordEncoder.encode(addUserRequest.password()))
                .phone(addUserRequest.phone())
                .role(addUserRequest.role())
                .build();
        Long userId = userRepository.save(user).getId();
        log.info("User {} added successfully.", userId);
        return userMapper.mapToUserResponse(user);
    }

    public UserResponse editUser(EditUserRequest editUserRequest, Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        validateUser(editUserRequest.username(), editUserRequest.email());
        existingUser.setFirstname(editUserRequest.firstname());
        existingUser.setLastname(editUserRequest.lastname());
        existingUser.setUsername(editUserRequest.username());
        existingUser.setEmail(editUserRequest.email());
        existingUser.setPhone(editUserRequest.phone());
        existingUser.setPassword(passwordEncoder.encode(editUserRequest.password()));
        existingUser.setRole(editUserRequest.role());
        userRepository.save(existingUser);
        log.info("User {} updated successfully.", existingUser.getId());
        return userMapper.mapToUserResponse(existingUser);
    }

}
