package com.medical.ehr.controllers;

import com.medical.ehr.dto.requests.LoginRequest;
import com.medical.ehr.dto.requests.RegisterRequest;
import com.medical.ehr.services.AuthService;
import com.medical.ehr.utils.SecurityLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SecurityLayer securityLayer;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/checkAdminPermission")
    public ResponseEntity<Object> checkAdminPermission() {
        System.out.println("Token received: " + SecurityContextHolder.getContext().getAuthentication().getCredentials()); // Debugging line
        securityLayer.authorizeAdmin();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUserIdAndRoleFromToken")
    public ResponseEntity<Object> getUserIdAndRoleFromToken() {
        return new ResponseEntity<>(securityLayer.getUserIdAndRoleFromToken(), HttpStatus.OK);
    }

}
