package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.UserResponse;
import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.UserInfo;
import com.rishi.leaveportal.repository.EmployeeRepository;
import com.rishi.leaveportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().name()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByFilters(String role, String department) {
        List<Employee> employees;

        if (role != null && department != null) {
            employees = employeeRepository.findByUserRoleAndDepartment(UserInfo.Role.valueOf(role.toUpperCase()), department);
        } else if (role != null) {
            employees = employeeRepository.findByUserRole(UserInfo.Role.valueOf(role.toUpperCase()));
        } else if (department != null) {
            employees = employeeRepository.findByDepartment(department);
        } else {
            employees = employeeRepository.findAll();
        }

        return employees.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserRole(String username, String newRole) {
        //ExceptionTODO
        UserInfo userInfo = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try{
            UserInfo.Role role = UserInfo.Role.valueOf(newRole.toUpperCase());
            userInfo.setRole(role);
            userRepository.save(userInfo);
        }catch (IllegalArgumentException e){
             throw new RuntimeException("Invalid role. Allowed role: EMPLOYEE,MANAGER,ADMIN");
        }
    }

    @Override
    public void deleteUser(String username) {
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }

    private UserResponse mapToUserResponse(Employee employee) {
        UserInfo user = employee.getUser();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }
}
