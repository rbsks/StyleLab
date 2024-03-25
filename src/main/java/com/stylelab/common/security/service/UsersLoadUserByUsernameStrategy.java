package com.stylelab.common.security.service;

import com.stylelab.common.security.principal.UserPrincipal;
import com.stylelab.user.domain.User;
import com.stylelab.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersLoadUserByUsernameStrategy implements LoadUserByUsernameStrategy {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);

        return UserPrincipal.create(user);
    }
}
