package com.stylelab.user.application;

import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.exception.UserError;
import com.stylelab.user.exception.UserException;

public record CreateUserDeliveryAddressCommand(
        Long userId,
        String address,
        String addressDetail,
        String postalCode,
        String addressAliases,
        Boolean defaultDeliveryAddress) {

    public CreateUserDeliveryAddressCommand {
        if (userId == null) {
            throw new UserException(UserError.INVALID_USER);
        }
    }

    public static CreateUserDeliveryAddressCommand create(
            Long userId, String address, String addressDetail, String postalCode, String addressAliases, Boolean defaultDeliveryAddress) {

        return new CreateUserDeliveryAddressCommand(
                userId, address, addressDetail, postalCode, addressAliases, defaultDeliveryAddress
        );
    }


    public UserDeliveryAddress saveUserDeliveryAddress() {

        return UserDeliveryAddress.builder()
                .userId(this.userId)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .postalCode(this.postalCode)
                .addressAliases(this.addressAliases)
                .defaultDeliveryAddress(this.defaultDeliveryAddress)
                .build();
    }
}
