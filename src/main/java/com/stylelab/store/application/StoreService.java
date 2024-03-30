package com.stylelab.store.application;

import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.infrastructure.StoreRepository;
import com.stylelab.store.infrastructure.StoreStaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreStaffRepository storeStaffRepository;
    private final PasswordEncoder passwordEncoder;

    public void applyStore(final CreateStoreCommand createStoreCommand, final CreateStoreStaffCommand createStoreStaffCommand) {


        if (!Objects.equals(createStoreStaffCommand.password(), createStoreStaffCommand.confirmPassword())) {
            throw new StoreException(StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT);
        }

        Store store = storeRepository.save(createStoreCommand.createStore());
        String encodePassword = passwordEncoder.encode(createStoreStaffCommand.password());
        StoreStaff storeStaff = storeStaffRepository.save(createStoreStaffCommand.createStoreStaff(store.storeId(), encodePassword));
    }

}
