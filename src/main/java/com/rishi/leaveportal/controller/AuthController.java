package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.AuthRequest;
import com.rishi.leaveportal.dto.AuthResponse;
import com.rishi.leaveportal.dto.RegisterRequest;
import com.rishi.leaveportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.badRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            AuthResponse authResponse = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));

        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request){
        try {
            AuthResponse authResponse = authService.registerAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));

        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
