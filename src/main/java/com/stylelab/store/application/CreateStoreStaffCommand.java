package com.stylelab.store.application;

import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;

public record CreateStoreStaffCommand(
        String email,
        String password,
        String confirmPassword,
        String name,
        String nickname,
        String phoneNumber) {

    public static CreateStoreStaffCommand create(
            String email, String password, String confirmPassword, String name, String nickname, String phoneNumber) {

        return new CreateStoreStaffCommand(email, password, confirmPassword, name, nickname, phoneNumber);
    }

    public StoreStaff createStoreStaff(Long storeId, String encodePassword) {

        return StoreStaff.builder()
                .storeId(storeId)
                .email(this.email)
                .password(encodePassword)
                .name(this.name)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .storeStaffRole(StoreStaffRole.ROLE_STORE_OWNER)
                .build();
    }
}
