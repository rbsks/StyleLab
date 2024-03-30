package com.stylelab.store.application;

import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.domain.Store;

public record CreateStoreCommand(
        String brand,
        String name,
        String address,
        String addressDetail,
        String postalCode,
        String businessLicenseNumber) {

    public static CreateStoreCommand create(
            String brand, String name, String address, String addressDetail, String postalCode, String businessLicenseNumber) {

        return new CreateStoreCommand(brand, name, address, addressDetail, postalCode, businessLicenseNumber);
    }

    public Store createStore() {

        return Store.builder()
                .brand(this.brand)
                .name(this.name)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .postalCode(this.postalCode)
                .businessLicenseNumber(this.businessLicenseNumber)
                .approveType(ApproveType.APPROVE)
                .build();
    }
}
