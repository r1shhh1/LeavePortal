package com.rishi.leaveportal.dto;

import com.rishi.leaveportal.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String department;
    private UserInfo.Role role;
}
