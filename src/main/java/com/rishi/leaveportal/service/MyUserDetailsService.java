package com.rishi.leaveportal.service;

import com.rishi.leaveportal.model.CustomUserDetails;
import com.rishi.leaveportal.model.UserInfo;
import com.rishi.leaveportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+ username));

        return new CustomUserDetails(userInfo);
    }
}
