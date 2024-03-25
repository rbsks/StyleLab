package com.stylelab.auth.presentation.response;

import com.stylelab.user.exception.UserError;
import com.stylelab.user.exception.UserException;
import org.springframework.util.StringUtils;

public record SignInResponse(String token) {

    public static SignInResponse create(String token) {
        if (!StringUtils.hasText(token)) {
            throw new UserException(UserError.EMAIL_IS_REQUIRED);
        }

        return new SignInResponse(token);
    }
}
