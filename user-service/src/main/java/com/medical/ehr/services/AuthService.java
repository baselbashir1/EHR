package com.medical.ehr.services;

import com.medical.ehr.dto.requests.LoginRequest;
import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.AuthResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import com.medical.ehr.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {

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
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect."));

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

}
