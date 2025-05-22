package com.rishi.leaveportal.service;

import com.rishi.leaveportal.model.Employee;
import com.rishi.leaveportal.model.UserInfo;
import com.rishi.leaveportal.repository.EmployeeRepository;
import com.rishi.leaveportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Override
    public void assignManager(String empUsername, String manUsername) throws Exception {
        Employee emp = employeeRepository.findByUserUsername(empUsername);

        Employee man = employeeRepository.findByUserUsername(manUsername);

        if(man.getUser().getRole() != UserInfo.Role.MANAGER &&
            emp.getDepartment().equals(man.getDepartment())){
            throw new Exception("Provided managerUsername is not a manager or the manager is from another department");
        }

        emp.setManager(man);
        employeeRepository.save(emp);


    }
}
