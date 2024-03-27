package com.stylelab.common.security.service;

import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.infrastructure.StoreStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StoreLoadUserByUsernameStrategy implements LoadUserByUsernameStrategy {

    private final StoreStaffRepository storeStaffRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoreStaff storeStaff = storeStaffRepository.findByEmail(username);
        return StorePrincipal.create(storeStaff);
    }
}
