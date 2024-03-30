package com.stylelab.user.presentation.request;

import com.stylelab.user.exception.UserError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String email,
        @NotBlank(message = "PASSWORD_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String password,
        @NotBlank(message = "CONFIRM_PASSWORD_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String confirmPassword,
        @NotBlank(message = "NAME_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^[가-힣]*$", message = "NAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String name,
        @NotBlank(message = "NICKNAME_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String nickname,
        @NotBlank(message = "PHONE_NUMBER_IS_REQUIRED", payload = UserError.class)
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
        String phoneNumber
) {

    @Override
    public String toString() {
        return "SignupRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
