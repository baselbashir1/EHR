package com.medical.ehr.services;

import com.medical.ehr.dto.requests.LoginRequest;
import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.LoginResponse;
import com.medical.ehr.dto.responses.RegisterResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import com.medical.ehr.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    // replace this with service
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public RegisterResponse register(RegisterRequest registerRequest) {

        Optional<User> existingUsername = userRepository.findByUsername(registerRequest.username());
        Optional<User> existingEmail = userRepository.findByEmail(registerRequest.email());

        if (existingUsername.isPresent()) {
            throw new IllegalArgumentException("Username already exist.");
        }

        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException("Email already exist.");
        }

        User user = User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phone(registerRequest.phone())
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new RegisterResponse(token, user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getPhone(), user.getRole());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.username());
        if (user.isPresent()) {
            if (passwordEncoder.matches(loginRequest.password(), user.get().getPassword())) {
                String token = jwtUtil.generateToken(user.get());
                return new LoginResponse(token);
            } else {
                throw new IllegalArgumentException("Username or password incorrect.");
            }
        } else {
            throw new IllegalArgumentException("Username or password incorrect.");
        }
    }

}
