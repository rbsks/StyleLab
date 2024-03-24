package com.stylelab.user.application;

import com.stylelab.user.exception.UserException;

import java.util.Objects;

import static com.stylelab.user.exception.UserError.PASSWORD_VERIFICATION_NOT_MATCH;

public record SignUpUser(
        String email,
        String password,
        String confirmPassword,
        String name,
        String nickname,
        String phoneNumber) {

    public static SignUpUser create(String email, String password, String confirmPassword, String name, String nickname, String phoneNumber) {

        if (!Objects.equals(password, confirmPassword)) {
            throw new UserException(PASSWORD_VERIFICATION_NOT_MATCH);
        }

        return new SignUpUser(email, password, confirmPassword, name, nickname, phoneNumber);
    }
}
