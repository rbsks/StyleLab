package com.stylelab.auth.application;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.auth.exception.AuthException;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.infrastructure.StoreStaffRepository;
import com.stylelab.user.domain.User;
import com.stylelab.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StoreStaffRepository storeStaffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String userSignIn(final SignInUser signInUser) {

        User user = userRepository.findByEmail(signInUser.email());

        if (!user.matchPassword(signInUser.password(), passwordEncoder)) {
            throw new AuthException(AuthError.INVALID_PASSWORD);
        }

        return jwtTokenProvider.createAuthToken(user.email(), user.role().name(), UserType.USER);
    }

    public String storeSignIn(final SignInUser signInUser) {

        StoreStaff storeStaff = storeStaffRepository.findByEmail(signInUser.email());

        if (!storeStaff.matchPassword(signInUser.password(), passwordEncoder)) {
            throw new AuthException(AuthError.INVALID_PASSWORD);
        }

        return jwtTokenProvider.createAuthToken(storeStaff.email(), storeStaff.storeStaffRole().name(), UserType.STORE);
    }

}
