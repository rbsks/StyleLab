package com.stylelab.category.infrastructure;

import com.stylelab.category.domain.ProductCategory;
import com.stylelab.category.infrastructure.dto.ProductCategoryCondition;
import com.stylelab.category.infrastructure.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepository {

    private final ProductCategoryJpaRepository productCategoryJpaRepository;
    private final ProductCategoryJdbcRepository productCategoryJdbcRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public List<ProductCategory> findAllCategories() {
        return productCategoryJpaRepository.findAll().stream()
                .map(productCategoryMapper::toProductCategories)
                .collect(Collectors.toList());
    }

    public Slice<ProductCategoryDto> findAllProductCategoryConditions(ProductCategoryCondition productCategoryCondition) {
        return productCategoryJdbcRepository.findAllProductCategoryConditions(productCategoryCondition);
    }
}
