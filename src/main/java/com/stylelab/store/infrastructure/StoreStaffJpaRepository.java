package com.stylelab.store.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreStaffJpaRepository extends JpaRepository<StoreStaffEntity, Long> {

    Optional<StoreStaffEntity> findByEmail(String email);
}
