package com.stylelab.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOption1JpaRepository extends JpaRepository<ProductOption1Entity, Long> {

    List<ProductOption1Entity> findAllByProductId(Long productId);
}
