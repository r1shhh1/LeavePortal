package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final LeaveService leaveService;

    @GetMapping("/leave-requests")
    public ResponseEntity<List<LeaveResponse>> getAllAssignedLeaveRequests(
            @AuthenticationPrincipal UserDetails userDetails
            ){
        try{
            List<LeaveResponse> responses = leaveService.getLeaveRequestsForManager(userDetails.getUsername());
            return ResponseEntity.ok(responses);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("leave/{id}/status")
    public ResponseEntity<?> updateLeaveStatus(
            @PathVariable long id,
            @RequestParam String status,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        try{
            leaveService.updateLeaveStatus(id,status,userDetails.getUsername());
            return ResponseEntity.ok("Leave status updated to "+ status);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error: "+ e.getMessage());
        }
    }
}
