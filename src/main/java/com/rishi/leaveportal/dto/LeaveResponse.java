package com.rishi.leaveportal.dto;


import com.rishi.leaveportal.model.LeaveRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveRequest.Status status;
    private LocalDateTime appliedAt;
}
