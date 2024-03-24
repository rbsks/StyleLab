package com.stylelab.user.domain;

public record UserDeliveryAddress(
        Long userAddressId,
        User user,
        String address,
        String addressDetail,
        String postalCode,
        String addressAliases,
        Boolean defaultDeliveryAddress) {

}
