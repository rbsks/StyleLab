package com.stylelab.auth.presentation.response;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.auth.exception.AuthException;
import org.springframework.util.StringUtils;

public record SignInResponse(String token) {

    public static SignInResponse create(String token) {
        if (!StringUtils.hasText(token)) {
            throw new AuthException(AuthError.FAIL_CREATE_JWT);
        }

        return new SignInResponse(token);
    }
}
