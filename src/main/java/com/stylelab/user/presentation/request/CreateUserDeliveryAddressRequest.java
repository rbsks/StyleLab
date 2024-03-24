package com.stylelab.user.presentation.request;

import com.stylelab.user.exception.UserError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserDeliveryAddressRequest(
        @NotBlank(message = "DELIVERY_ADDRESS_REQUIRE", payload = UserError.class)
        String address,
        @NotBlank(message = "DELIVERY_ADDRESS_DETAIL_REQUIRE", payload = UserError.class)
        String addressDetail,
        @NotBlank(message = "DELIVERY_POSTAL_CODE_REQUIRE", payload = UserError.class)
        String postalCode,
        @NotBlank(message = "DELIVERY_ADDRESS_ALIASES_REQUIRE", payload = UserError.class)
        String addressAliases,
        @NotNull(message = "DELIVERY_DEFAULT_ADDRESS_REQUIRE", payload = UserError.class)
        Boolean defaultDeliveryAddress
) {

    @Override
    public String toString() {
        return "CreateUserDeliveryAddressRequest{" +
                "address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", addressAliases='" + addressAliases + '\'' +
                ", defaultDeliveryAddress=" + defaultDeliveryAddress +
                '}';
    }
}
