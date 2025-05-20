package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.LeaveApplyRequest;
import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.model.Employee;

import java.util.List;

public interface LeaveService {
    LeaveResponse applyLeave(LeaveApplyRequest leaveRequest, String username) throws Exception;
    List<LeaveResponse> getLeaveRequestsForManager(String username) throws Exception;
    void updateLeaveStatus(Long requestId, String status, String managerUsername) throws Exception;
    List<LeaveResponse> getLeaveRequestsForEmployee(String username);

}
