package com.stylelab.auth.exception;

import com.stylelab.common.exception.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AuthError implements CommonError {

    EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "74000", "올바르지 않은 형식의 이메일입니다."),
    PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "74001", "올바르지 않은 형식의 비밀번호입니다."),
    PASSWORD_IS_REQUIRED(HttpStatus.BAD_REQUEST, "74002", "비밀번호는 필수입니다."),
    EMAIL_IS_REQUIRED(HttpStatus.BAD_REQUEST, "74003", "이메일은 필수입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "74004", "올바르지 않은 비밀번호입니다."),

    FAIL_CREATE_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "74050", "토큰 생성에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static AuthError of(String error) {
        return Arrays.stream(AuthError.values())
                .filter(authError -> authError.name().equalsIgnoreCase(error))
                .findAny()
                .orElse(null);
    }
}
