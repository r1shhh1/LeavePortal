package com.rishi.leaveportal.repository;

import com.rishi.leaveportal.dto.LeaveResponse;
import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {


    List<LeaveRequest> findByEmployee_Manager(Employee manager);

    List<LeaveResponse> findByEmployee(Employee employee);
}
