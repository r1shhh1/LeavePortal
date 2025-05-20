package com.rishi.leaveportal.repository;

import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByUser(UserInfo user);

    Employee findByUserUsername(String username);

    List<Employee> findByUserRoleAndDepartment(UserInfo.Role role, String department);

    List<Employee> findByUserRole(UserInfo.Role role);

    List<Employee> findByDepartment(String department);
}
