package com.medical.ehr.services;

import com.medical.ehr.dto.requests.LoginRequest;
import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.LoginResponse;
import com.medical.ehr.dto.responses.RegisterResponse;
import com.medical.ehr.mappers.AuthMapper;
import com.medical.ehr.models.User;
import com.medical.ehr.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        userService.validateUser(registerRequest.username(), registerRequest.email());

        User user = userService.save(authMapper.mapToUser(registerRequest));
        log.info("User {} registered successfully.", user.getId());

        String token = jwtUtil.generateToken(user);
        return authMapper.mapToRegisterResponse(user, token);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.loadUserByUsername(loginRequest.username());

        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new IllegalArgumentException("Username or password incorrect.");
        }

        String token = jwtUtil.generateToken(user);
        log.info("User {} logged in.", user.getId());
        return authMapper.mapToLoginResponse(user, token);
    }

}
