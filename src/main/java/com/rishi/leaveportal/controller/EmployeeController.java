package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.repository.LeaveRequestRepository;
import com.rishi.leaveportal.service.EmployeeService;
import com.rishi.leaveportal.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveService leaveService;

    @GetMapping("/leave-history")
    public ResponseEntity<List<LeaveResponse>> fetchLeaveRequest(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        try{
            List<LeaveResponse> history = leaveService.getLeaveRequestsForEmployee(userDetails.getUsername());
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
