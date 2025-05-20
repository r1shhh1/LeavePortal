package com.rishi.leaveportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplyRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
