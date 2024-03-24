package com.stylelab.user.application;

import com.stylelab.user.exception.UserError;
import com.stylelab.user.exception.UserException;

public record CreateUserDeliveryAddress(
        Long userId,
        String address,
        String addressDetail,
        String postalCode,
        String addressAliases,
        Boolean defaultDeliveryAddress) {

    public CreateUserDeliveryAddress {
        if (userId == null) {
            throw new UserException(UserError.INVALID_USER);
        }
    }

    public static CreateUserDeliveryAddress create(
            Long userId, String address, String addressDetail, String postalCode, String addressAliases, Boolean defaultDeliveryAddress) {

        return new CreateUserDeliveryAddress(
                userId, address, addressDetail, postalCode, addressAliases, defaultDeliveryAddress
        );
    }
}
