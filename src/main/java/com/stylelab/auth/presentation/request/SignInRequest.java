package com.stylelab.auth.presentation.request;

import com.stylelab.auth.exception.AuthError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotBlank(message = "EMAIL_IS_REQUIRED", payload = AuthError.class)
        @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = AuthError.class)
        String email,
        @NotBlank(message = "PASSWORD_IS_REQUIRED", payload = AuthError.class)
        @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = AuthError.class)
        String password
) {

    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
