package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.AuthRequest;
import com.rishi.leaveportal.dto.AuthResponse;
import com.rishi.leaveportal.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse registerAdmin(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest authRequest);
}
