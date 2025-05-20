package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.UserResponse;
import com.rishi.leaveportal.repository.EmployeeRepository;
import com.rishi.leaveportal.service.EmployeeService;
import com.rishi.leaveportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final EmployeeService employeeService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String department
    ){
        return ResponseEntity.ok(userService.getUsersByFilters(role, department));
    }


    @PutMapping("/users/{username}/role")
    public ResponseEntity<String> updateUserRole(
            @PathVariable String username,
            @RequestParam String role
    ){
        try{
            userService.updateUserRole(username,role);
            return ResponseEntity.ok("Role updated successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/assign-manager")
    public ResponseEntity<String> assignManager(@RequestParam String empUsername,
                                                @RequestParam String managerUsername){
        try{
            employeeService.assignManager(empUsername,managerUsername);
            return ResponseEntity.ok("Manager assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        try{
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
