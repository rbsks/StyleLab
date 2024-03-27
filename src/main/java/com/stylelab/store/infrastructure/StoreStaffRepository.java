package com.stylelab.store.infrastructure;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.store.domain.StoreStaff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Repository
@RequiredArgsConstructor
public class StoreStaffRepository {

    private final StoreMapper storeMapper;
    private final StoreStaffJpaRepository storeStaffJpaRepository;

    public StoreStaff findByEmail(String email) {

        return storeMapper.toStoreStaff(
                storeStaffJpaRepository.findByEmail(email)
                        .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()))
        );
    }

    public StoreStaff save(StoreStaff storeStaff) {

        return storeMapper.toStoreStaff(
                storeStaffJpaRepository.save(storeMapper.toStoreStaffEntity(storeStaff))
        );
    }
}
