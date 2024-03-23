package com.stylelab.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryEntity, Long> {
}
