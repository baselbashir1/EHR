package com.medical.ehr.services;

import com.medical.ehr.dto.requests.EditUserRequest;
import com.medical.ehr.dto.requests.AddUserRequest;
import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.mappers.UserMapper;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import com.medical.ehr.utils.SecurityLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityLayer securityLayer;

    public User saveUser(User user) {
        return userRepository.save(userMapper.mapToUser(user));
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

    public void addUser(AddUserRequest addUserRequest) {
        securityLayer.authorizeAdmin();
        validateUser(addUserRequest.username(), addUserRequest.email());
        User user = userMapper.mapToUser(addUserRequest);
        User savedUser = userRepository.save(user);
        log.info("User {} added successfully.", savedUser.getId());
    }

    public void editUser(EditUserRequest editUserRequest, Long userId) {
        securityLayer.authorizeAdmin();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        validateUser(editUserRequest.username(), editUserRequest.email());
        userMapper.mapToUser(user, editUserRequest);
        userRepository.save(user);
        log.info("User {} updated successfully.", user.getId());
    }

    public void deleteUser(Long userId) {
        securityLayer.authorizeAdmin();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        userRepository.delete(user);
        log.info("User {} deleted successfully.", user.getId());
    }

}
