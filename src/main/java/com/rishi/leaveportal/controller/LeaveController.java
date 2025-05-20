package com.rishi.leaveportal.controller;

import com.rishi.leaveportal.dto.LeaveApplyRequest;
import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<?> applyLeave(@RequestBody LeaveApplyRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails){

        try{
            LeaveResponse response = leaveService.applyLeave(request,userDetails.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+ e.getMessage());
        }
    }

}
