package com.stylelab.store.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, Long> {
}
