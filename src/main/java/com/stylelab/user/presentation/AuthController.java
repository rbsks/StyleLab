package com.stylelab.user.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.user.application.AuthService;
import com.stylelab.user.application.SignInUser;
import com.stylelab.user.application.SignUpUser;
import com.stylelab.user.exception.UserError;
import com.stylelab.user.presentation.request.SignInRequest;
import com.stylelab.user.presentation.request.SignupRequest;
import com.stylelab.user.presentation.response.ExistsByEmailResponse;
import com.stylelab.user.presentation.response.ExistsByNicknameResponse;
import com.stylelab.user.presentation.response.SignInResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid final SignupRequest signupRequest) {
        SignUpUser signUpUser =
                SignUpUser.create(
                        signupRequest.email(), signupRequest.password(), signupRequest.confirmPassword(),
                        signupRequest.name(), signupRequest.nickname(), signupRequest.phoneNumber()
                );

        authService.signup(signUpUser);

        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.CREATED);
    }

    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<ExistsByEmailResponse>> existsByEmail(
            @RequestParam(name = "email", required = false)
            @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class)
            @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
            final String email) {

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        ExistsByEmailResponse.create(
                                authService.existsByEmail(email)
                        )
                )
        );
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<ExistsByNicknameResponse>> existsByNickname(
            @RequestParam(name = "nickname", required = false)
            @NotBlank(message = "NICKNAME_IS_REQUIRED", payload = UserError.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UserError.class)
            final String nickname) {

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        ExistsByNicknameResponse.create(
                                authService.existsByNickname(nickname)
                        )
                )
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInResponse>> sigIn(@RequestBody @Valid final SignInRequest signInRequest) {

        SignInUser signInUser =
                SignInUser.create(
                        signInRequest.email(), signInRequest.password()
                );

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        SignInResponse.create(
                                authService.signIn(signInUser)
                        )
                )
        );
    }
}
