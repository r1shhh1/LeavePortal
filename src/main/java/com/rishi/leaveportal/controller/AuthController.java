package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.AuthRequest;
import com.rishi.leaveportal.dto.AuthResponse;
import com.rishi.leaveportal.dto.RegisterRequest;
import com.rishi.leaveportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            return ResponseEntity.ok(authService.register(request));
        }catch (Exception e){

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        }catch (Exception e){

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
