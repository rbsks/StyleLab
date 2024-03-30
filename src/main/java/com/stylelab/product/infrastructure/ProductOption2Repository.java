package com.stylelab.product.infrastructure;

import com.stylelab.product.domain.ProductOption2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductOption2Repository {

    private final ProductMapper productMapper;
    private final ProductOption2JpaRepository productOption2JpaRepository;

    public List<ProductOption2> saveAll(List<ProductOption2> productOption2s) {

        List<ProductOption2Entity> productOption2Entities = productOption2JpaRepository.saveAll(
                productOption2s.stream()
                        .map(productMapper::mapProductOption2ToEntity)
                        .toList()
        );

        return productOption2Entities.stream()
                .map(productMapper::mapProductOption2EntityToDomain)
                .toList();
    }
}
