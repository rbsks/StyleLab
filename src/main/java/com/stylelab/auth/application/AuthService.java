package com.stylelab.auth.application;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.repository.StoreStaffRepository;
import com.stylelab.user.domain.User;
import com.stylelab.user.exception.UserException;
import com.stylelab.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

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

        validationPassword(signInUser.password(), user.password());

        return jwtTokenProvider.createAuthToken(user.email(), user.role().name(), UserType.USER);
    }

    public String storeSignIn(final SignInUser signInUser) {

        StoreStaff storeStaff = storeStaffRepository.findByEmail(signInUser.email())
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()));

        validationPassword(signInUser.password(), storeStaff.getPassword());

        return jwtTokenProvider.createAuthToken(storeStaff.getEmail(), storeStaff.getStoreStaffRole().name(), UserType.STORE);
    }

    private void validationPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserException(AuthError.INVALID_PASSWORD);
        }
    }
}
