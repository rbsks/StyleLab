package com.stylelab.store.domain;

import com.stylelab.store.constant.StoreStaffRole;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Builder
public record StoreStaff(
        Long storeStaffId,
        Long storeId,
        String email,
        String password,
        String name,
        String nickname,
        String phoneNumber,
        StoreStaffRole storeStaffRole,
        boolean withdrawal,
        LocalDateTime withdrawalAt) {

    public boolean matchPassword(String requestPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(requestPassword, this.password);
    }
}
