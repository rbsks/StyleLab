package com.stylelab.user.domain;

import lombok.Builder;

@Builder
public record UserDeliveryAddress(
        Long userAddressId,
        User user,
        String address,
        String addressDetail,
        String postalCode,
        String addressAliases,
        Boolean defaultDeliveryAddress) {

}
