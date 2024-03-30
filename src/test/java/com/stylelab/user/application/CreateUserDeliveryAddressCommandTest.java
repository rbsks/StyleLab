package com.stylelab.user.application;

import com.stylelab.user.exception.UserError;
import com.stylelab.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CreateUserDeliveryAddressCommandTest {

    private Long userId;
    private String address;
    private String addressDetail;
    private String postalCode;
    private String addressAliases;
    private Boolean defaultDeliveryAddress;

    @Test
    @DisplayName("userId가 null 인 경우 CreateUserDeliveryAddress 객체 생성에 실패한다.")
    public void failCreateUserDeliveryAddress() throws Exception {
        //given

        //when
        Throwable throwable = catchThrowable(() ->
                CreateUserDeliveryAddressCommand.create(userId, address, addressDetail, postalCode, addressAliases, defaultDeliveryAddress)
        );

        //then
        assertThat(throwable).isInstanceOf(UserException.class);
        assertThat(throwable.getMessage()).isEqualTo(UserError.INVALID_USER.getMessage());
    }
}
