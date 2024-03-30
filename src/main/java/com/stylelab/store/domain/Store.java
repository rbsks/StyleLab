package com.stylelab.store.domain;

import com.stylelab.store.constant.ApproveType;
import lombok.Builder;

@Builder
public record Store(
        Long storeId,
        String brand,
        String name,
        String address,
        String addressDetail,
        String postalCode,
        String businessLicenseNumber,
        ApproveType approveType) {

}
