package com.rishi.leaveportal.repository;

import com.rishi.leaveportal.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    boolean existsByUsername(String username);
}
