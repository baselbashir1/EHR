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

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    public RegisterResponse register(RegisterRequest registerRequest) {
        userService.validateUser(registerRequest);

        User user = userService.registerUser(registerRequest);
        log.info("User {} saved successfully.", user.getId());

        String token = jwtUtil.generateToken(user);
        return authMapper.mapToRegisterResponse(user, token);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.loadUserByUsername(loginRequest.username());

        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new IllegalArgumentException("Username or password incorrect.");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token);
    }

}
