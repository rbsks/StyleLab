package com.stylelab.user.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.security.principal.UserPrincipal;
import com.stylelab.user.application.CreateUserDeliveryAddress;
import com.stylelab.user.application.UserDeliveryAddressService;
import com.stylelab.user.presentation.request.CreateUserDeliveryAddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDeliveryAddressService userDeliveryAddressService;

    @PostMapping("/deliveries")
    public ResponseEntity<ApiResponse<Void>> createUserDeliveryAddress(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid final CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest) {

        CreateUserDeliveryAddress createUserDeliveryAddress =
                CreateUserDeliveryAddress.create(
                        userPrincipal.getUserId(), createUserDeliveryAddressRequest.address(), createUserDeliveryAddressRequest.addressDetail(),
                        createUserDeliveryAddressRequest.postalCode(), createUserDeliveryAddressRequest.addressAliases(), createUserDeliveryAddressRequest.defaultDeliveryAddress()
        );

        userDeliveryAddressService.createUserDeliveryAddress(createUserDeliveryAddress);

        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.NO_CONTENT);
    }
}
