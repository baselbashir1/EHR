package com.medical.ehr.services;

import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.dto.responses.AuthResponse;
import com.medical.ehr.enums.UserRole;
import com.medical.ehr.models.User;
import com.medical.ehr.repositories.UserRepository;
import com.medical.ehr.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

}
