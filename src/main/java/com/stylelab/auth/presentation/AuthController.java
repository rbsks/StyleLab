package com.stylelab.auth.presentation;

import com.stylelab.auth.application.AuthService;
import com.stylelab.auth.application.SignInUser;
import com.stylelab.auth.presentation.request.SignInRequest;
import com.stylelab.auth.presentation.response.SignInResponse;
import com.stylelab.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/signin")
    public ResponseEntity<ApiResponse<SignInResponse>> userSigIn(@RequestBody @Valid final SignInRequest signInRequest) {

        SignInUser signInUser =
                SignInUser.create(
                        signInRequest.email(), signInRequest.password()
                );

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        SignInResponse.create(
                                authService.userSignIn(signInUser)
                        )
                )
        );
    }

    @PostMapping("/stores/signin")
    public ResponseEntity<ApiResponse<SignInResponse>> storeSigIn(@RequestBody @Valid final SignInRequest signInRequest) {

        SignInUser signInUser =
                SignInUser.create(
                        signInRequest.email(), signInRequest.password()
                );

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        SignInResponse.create(
                                authService.storeSignIn(signInUser)
                        )
                )
        );
    }
}
