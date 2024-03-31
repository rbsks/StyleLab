package com.stylelab.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOption2JpaRepository extends JpaRepository<ProductOption2Entity, Long> {

    List<ProductOption2Entity> findAllByProductOption1Id(Long productOption1Id);
}
