package com.stylelab.auth.presentation.response;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.auth.exception.AuthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SigInResponseTest {

    @Test
    @DisplayName("access token이 담긴 로그인 응답 객체 생성에 성공.")
    public void successCreateResponse() {
        // given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE3MDM2OTU0MzV9.FkF6Dq6sM-krPxm5CzYE4qknlBZsou7n1GPd3L9rTd";

        // when
        SignInResponse response = SignInResponse.create(token);

        // then
        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(token);
    }

    @Test
    @DisplayName("token이 null인 경우 로그인 응답 객체 생성에 실패한다.")
    public void failureCreateResponse() {
        // given
        String token = null;

        // when
        Throwable throwable = catchThrowable(() ->
                SignInResponse.create(token));

        // then
        assertThat(throwable).isInstanceOf(AuthException.class);
        assertThat(throwable.getMessage()).isEqualTo(AuthError.FAIL_CREATE_JWT.getMessage());
    }
}
