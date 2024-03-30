package com.stylelab.store.infrastructure;

import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public StoreEntity mapStoreToEntity(Store store) {

        return StoreEntity.builder()
                .storeId(store.storeId())
                .brand(store.brand())
                .name(store.name())
                .address(store.address())
                .addressDetail(store.addressDetail())
                .postalCode(store.postalCode())
                .businessLicenseNumber(store.businessLicenseNumber())
                .approveType(store.approveType())
                .build();
    }

    public StoreStaffEntity mapStoreStaffToEntity(StoreStaff storeStaff) {

        return StoreStaffEntity.builder()
                .storeId(storeStaff.storeId())
                .email(storeStaff.email())
                .password(storeStaff.password())
                .name(storeStaff.name())
                .nickname(storeStaff.nickname())
                .phoneNumber(storeStaff.phoneNumber())
                .storeStaffRole(storeStaff.storeStaffRole())
                .withdrawal(storeStaff.withdrawal())
                .withdrawalAt(storeStaff.withdrawalAt())
                .build();
    }

    public Store mapStoreEntityToDomain(StoreEntity storeEntity) {

        return Store.builder()
                .storeId(storeEntity.getStoreId())
                .brand(storeEntity.getBrand())
                .name(storeEntity.getName())
                .address(storeEntity.getAddress())
                .addressDetail(storeEntity.getAddressDetail())
                .postalCode(storeEntity.getPostalCode())
                .businessLicenseNumber(storeEntity.getBusinessLicenseNumber())
                .approveType(storeEntity.getApproveType())
                .build();
    }


    public StoreStaff mapStoreStaffEntityToDomain(StoreStaffEntity storeStaffEntity) {

        return StoreStaff.builder()
                .storeStaffId(storeStaffEntity.getStoreStaffId())
                .storeId(storeStaffEntity.getStoreId())
                .email(storeStaffEntity.getEmail())
                .password(storeStaffEntity.getPassword())
                .name(storeStaffEntity.getName())
                .phoneNumber(storeStaffEntity.getPhoneNumber())
                .storeStaffRole(storeStaffEntity.getStoreStaffRole())
                .withdrawal(storeStaffEntity.isWithdrawal())
                .withdrawalAt(storeStaffEntity.getWithdrawalAt())
                .build();
    }
}
