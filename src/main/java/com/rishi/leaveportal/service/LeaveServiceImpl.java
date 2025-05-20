package com.rishi.leaveportal.service;

import com.rishi.leaveportal.dto.LeaveApplyRequest;
import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.LeaveRequest;
import com.rishi.leaveportal.model.UserInfo;
import com.rishi.leaveportal.repository.EmployeeRepository;
import com.rishi.leaveportal.repository.LeaveRequestRepository;
import com.rishi.leaveportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Override
    public LeaveResponse applyLeave(LeaveApplyRequest leaveApplyRequest, String username) throws Exception {
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));

        Employee employee = employeeRepository.findByUser(user);

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStartDate(leaveApplyRequest.getStartDate());
        leaveRequest.setEndDate(leaveApplyRequest.getEndDate());
        leaveRequest.setReason(leaveApplyRequest.getReason());
        leaveRequest.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest.setAppliedAt(LocalDateTime.now());
        leaveRequest.setEmployee(employee);

        leaveRequestRepository.save(leaveRequest);

        return new LeaveResponse(
                leaveRequest.getId(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus(),
                leaveRequest.getAppliedAt()
        );
    }

    @Override
    public List<LeaveResponse> getLeaveRequestsForManager(String username) throws Exception {
        UserInfo userInfo = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Employee manager = employeeRepository.findByUser(userInfo);

        if(manager==null){
            throw new Exception("Manager employee record not found");
        }

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee_Manager(manager);

        return leaveRequests.stream()
                .map(lr -> new LeaveResponse(
                        lr.getId(),
                        lr.getStartDate(),
                        lr.getEndDate(),
                        lr.getReason(),
                        lr.getStatus(),
                        lr.getAppliedAt()
                )).toList();
    }

    @Override
    public void updateLeaveStatus(Long requestId, String status, String managerUsername) throws Exception {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new Exception("Leave request not found"));

        Employee manager = employeeRepository.findByUserUsername(managerUsername);

        if(manager == null || request.getEmployee().getManager() == null ||
                !request.getEmployee().getManager().getId().equals(manager.getId())
        ) {
            throw new Exception("Not authorized to update this request");
        }

        LeaveRequest.Status newStatus;
        try {
            newStatus = LeaveRequest.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid status. Use APPROVED or REJECTED.");
        }

        if (newStatus == LeaveRequest.Status.PENDING) {
            throw new Exception("Cannot set status back to PENDING.");
        }

        request.setStatus(newStatus);
        leaveRequestRepository.save(request);
    }

    @Override
    public List<LeaveResponse> getLeaveRequestsForEmployee(String username) {
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user);

        List<LeaveResponse> response = leaveRequestRepository.findByEmployee(employee);

        return response.stream()
                .map(leave -> new LeaveResponse(
                        leave.getId(),
                        leave.getStartDate(),
                        leave.getEndDate(),
                        leave.getReason(),
                        leave.getStatus(),
                        leave.getAppliedAt()
                )).toList();
    }
}
