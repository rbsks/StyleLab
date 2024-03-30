package com.stylelab.store.domain;

import com.stylelab.store.constant.StoreStaffRole;
import lombok.Builder;

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

}
