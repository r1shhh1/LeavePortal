package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.AuthRequest;
import com.rishi.leaveportal.dto.AuthResponse;
import com.rishi.leaveportal.dto.RegisterRequest;
import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.UserInfo;
import com.rishi.leaveportal.repository.EmployeeRepository;
import com.rishi.leaveportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;

    @Override
    public AuthResponse register(RegisterRequest registerRequest){
        UserInfo user = new UserInfo();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserInfo.Role.EMPLOYEE);
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setName(registerRequest.getName());
        employee.setDepartment(registerRequest.getDepartment());
        employeeRepository.save(employee);


        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest registerRequest) {
        UserInfo user = new UserInfo();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserInfo.Role.ADMIN);
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setName(registerRequest.getName());
        employee.setDepartment(registerRequest.getDepartment());
        employeeRepository.save(employee);


        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );

        }catch (AuthenticationException e){
            throw new RuntimeException("Invalid username or passowrd");
        }

        String token = jwtService.generateToken(authRequest.getUsername());
        return new AuthResponse(token);
    }
}
