package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByFilters(String role, String department);
    void updateUserRole(String username, String newRole);
    void deleteUser(String username);

}
