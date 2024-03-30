package com.stylelab.user.domain;

import com.stylelab.user.constant.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        Long userId,
        String email,
        String password,
        String name,
        String nickname,
        String phoneNumber,
        UserRole role,
        boolean withdrawal,
        LocalDateTime withdrawalAt) {

}
