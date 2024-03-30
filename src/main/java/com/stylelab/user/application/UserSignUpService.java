package com.stylelab.user.application;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.user.exception.UserException;
import com.stylelab.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.stylelab.common.exception.ServiceError.BAD_REQUEST;
import static com.stylelab.user.exception.UserError.USERS_SAVE_FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(final SignUpUserCommand signUpUserCommand) {

        String encodePassword = passwordEncoder.encode(signUpUserCommand.password());

        try {
            userRepository.save(
                    signUpUserCommand.createSignUpUser(encodePassword)
            );
        } catch (DataAccessException exception) {
            throw new UserException(USERS_SAVE_FAIL, exception);
        } catch (RuntimeException exception) {
            throw new ServiceException(BAD_REQUEST, exception);
        }
    }

    public boolean existsByEmail(final String email) {

        return userRepository.existsByEmail(email);
    }

    public boolean existsByNickname(final String nickname) {

        return userRepository.existsByNickname(nickname);
    }
}
