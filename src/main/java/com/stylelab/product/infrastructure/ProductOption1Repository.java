package com.stylelab.product.infrastructure;

import com.stylelab.product.domain.ProductOption1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductOption1Repository {

    private final ProductMapper productMapper;
    private final ProductOption1JpaRepository productOption1JpaRepository;

    public ProductOption1 save(ProductOption1 productOption1) {

        return productMapper.mapProductOption1EntityToDomain(
                productOption1JpaRepository.save(
                        productMapper.mapProductOption1ToEntity(productOption1)
                )
        )    ;
    }

    public List<ProductOption1> saveAll(List<ProductOption1> productOption1s) {

        List<ProductOption1Entity> productOption1Entities = productOption1JpaRepository.saveAll(
                productOption1s.stream()
                        .map(productMapper::mapProductOption1ToEntity)
                        .toList()
        );

        return productOption1Entities.stream()
                .map(productMapper::mapProductOption1EntityToDomain)
                .toList();
    }

    public List<ProductOption1> findAllByProductId(Long productId) {

        return productOption1JpaRepository.findAllByProductId(productId).stream()
                .map(productMapper::mapProductOption1EntityToDomain)
                .toList();
    }
}
