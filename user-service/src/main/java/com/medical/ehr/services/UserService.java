package com.medical.ehr.services;

import com.medical.ehr.dto.requests.EditUserRequest;
import com.medical.ehr.dto.requests.AddUserRequest;
import com.medical.ehr.dto.responses.UserResponse;
import com.medical.ehr.dto.responses.UserRoleResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.mappers.UserMapper;
import com.medical.ehr.models.Doctor;
import com.medical.ehr.models.Secretary;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.DoctorRepository;
import com.medical.ehr.repositories.SecretaryRepository;
import com.medical.ehr.repositories.UserRepository;
import com.medical.ehr.utils.SecurityLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final SecretaryRepository secretaryRepository;
    private final UserMapper userMapper;
    private final SecurityLayer securityLayer;

    public User save(User user) {
        return userRepository.save(user);
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

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> showUsers() {
        UserRoleResponse userIdAndRole = securityLayer.getUserIdAndRoleFromToken();
        Long userId = userIdAndRole.userId();
        UserRole userRole = userIdAndRole.role();

        if (userRole.equals(UserRole.ADMIN)) {
            return getAllUsers();
        }

//        if (userRole.equals(UserRole.DOCTOR)) {
//            return getUsersRelatedToDoctor(userId);
//        }
//
//        if (userRole.equals(UserRole.SECRETARY)) {
//            return getUsersForSecretaryRelatedToDoctor(userId);
//        }

        return List.of();
    }

    @Transactional
    public void addUser(AddUserRequest addUserRequest) {
        securityLayer.authorizeAdmin();
        validateUser(addUserRequest.username(), addUserRequest.email());
        User user = userMapper.mapToUser(addUserRequest);
        User addedUser = save(user);
        insertUserToTargetTable(user, addUserRequest);
        log.info("User {} added successfully.", addedUser.getId());
    }

    @Transactional
    public void editUser(EditUserRequest editUserRequest, Long userId) {
        securityLayer.authorizeAdmin();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        validateUser(editUserRequest.username(), editUserRequest.email());
        User updatedUser = userMapper.mapToUser(user, editUserRequest);
        save(updatedUser);
        log.info("User {} updated successfully.", updatedUser.getId());
    }

    @Transactional
    public void deleteUser(Long userId) {
        securityLayer.authorizeAdmin();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        userRepository.delete(user);
        log.info("User {} deleted successfully.", user.getId());
    }

    @Transactional
    public void editProfile(EditUserRequest editUserRequest) {
        UserRoleResponse userIdAndRole = securityLayer.getUserIdAndRoleFromToken();
        User user = userRepository.findById(userIdAndRole.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        validateUser(editUserRequest.username(), editUserRequest.email());
        User updatedUser = userMapper.mapToUser(user, editUserRequest);
        save(updatedUser);
        log.info("Profile updated successfully.");
    }

    public void insertUserToTargetTable(User user, AddUserRequest addUserRequest) {
        if (addUserRequest.role().equals(UserRole.DOCTOR)) {
            Doctor doctor = userMapper.mapToDoctor(user, addUserRequest);
            doctorRepository.save(doctor);
            log.info("Doctor added successfully.");
        }

        if (addUserRequest.role().equals(UserRole.SECRETARY)) {
            Secretary secretary = userMapper.mapToSecretary(user, addUserRequest);
            secretaryRepository.save(secretary);
            log.info("Secretary added successfully.");
        }
    }

}
