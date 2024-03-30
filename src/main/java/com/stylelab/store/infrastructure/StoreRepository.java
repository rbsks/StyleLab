package com.stylelab.store.infrastructure;

import com.stylelab.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final StoreMapper storeMapper;
    private final StoreJpaRepository storeJpaRepository;

    public Store save(Store store) {

        return storeMapper.mapStoreEntityToDomain(
                storeJpaRepository.save(storeMapper.mapStoreToEntity(store))
        );
    }
}
