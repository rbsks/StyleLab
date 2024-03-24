package com.stylelab.user.infrastructure;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public User save(User user) {

        return userMapper.toUser(
                userJpaRepository.save(userMapper.toUserEntity(user))
        );
    }

    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    public User findByEmail(String email) {

        return userMapper.toUser(
                userJpaRepository.findByEmail(email)
                        .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()))
        );
    }
}
