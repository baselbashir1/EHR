package com.medical.ehr.services;

import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.mappers.UserMapper;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

    public void validateUser(RegisterRequest registerRequest) {
        if (loadUserByUsername(registerRequest.username()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (loadUserByEmail(registerRequest.email()) != null) {
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

}
