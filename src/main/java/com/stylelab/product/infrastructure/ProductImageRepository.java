package com.stylelab.product.infrastructure;

import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductImageRepository {

    private final ProductMapper productMapper;
    private final ProductImageJpaRepository productImageJpaRepository;
    private final ProductImageQueryRepository productImageQueryRepository;

    public List<ProductImage> saveAll(List<ProductImage> productImages) {

        List<ProductImageEntity> productImageEntities = productImageJpaRepository.saveAll(
                productImages.stream()
                        .map(productMapper::mapProductImageToEntity)
                        .toList()
        );

        return productImageEntities.stream()
                .map(productMapper::mapProductImageEntityToDomain)
                .toList();
    }

    public List<ProductDetailImage> findAllByProductId(final Long productId) {

        return productImageQueryRepository.findAllByProductId(productId);
    }
}
