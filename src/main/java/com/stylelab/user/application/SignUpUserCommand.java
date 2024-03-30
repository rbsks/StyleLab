package com.stylelab.user.application;

import com.stylelab.user.constant.UserRole;
import com.stylelab.user.domain.User;
import com.stylelab.user.exception.UserException;

import java.util.Objects;

import static com.stylelab.user.exception.UserError.PASSWORD_VERIFICATION_NOT_MATCH;

public record SignUpUserCommand(
        String email,
        String password,
        String confirmPassword,
        String name,
        String nickname,
        String phoneNumber) {

    public static SignUpUserCommand create(String email, String password, String confirmPassword, String name, String nickname, String phoneNumber) {

        if (!Objects.equals(password, confirmPassword)) {
            throw new UserException(PASSWORD_VERIFICATION_NOT_MATCH);
        }

        return new SignUpUserCommand(email, password, confirmPassword, name, nickname, phoneNumber);
    }

    public User createSignUpUser(String encodePassword) {

        return User.builder()
                .email(this.email)
                .password(encodePassword)
                .name(this.name)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .role(UserRole.ROLE_USER)
                .build();
    }
}
