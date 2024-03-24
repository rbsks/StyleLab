package com.stylelab.user.application;

import com.stylelab.user.exception.UserError;
import com.stylelab.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SIgnUpUserTest {

    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String nickname;
    private String phoneNumber;

    @Test
    @DisplayName("password와 confirmPassword가 같지 않으면 SignUpUser 객체 생성에 실패한다.")
    public void failSignUpUser() throws Exception {
        //given
        password = "password";
        confirmPassword = "confirmPassword";

        //when
        Throwable throwable = catchThrowable(() ->
                SignUpUser.create(email, password, confirmPassword, name, nickname, phoneNumber)
        );

        //then
        assertThat(throwable).isInstanceOf(UserException.class);
        assertThat(throwable.getMessage()).isEqualTo(UserError.PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
    }
}
