package com.stylelab.product.infrastructure;

import com.stylelab.product.domain.Product;
import com.stylelab.product.infrastructure.dto.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductMapper productMapper;
    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;

    public Product save(Product product) {

        return productMapper.mapProductEntityToDomain(
                productJpaRepository.save(
                        productMapper.mapProductToEntity(product)
                )
        );
    }

    public Optional<ProductDetail> findByProductId(final Long productId) {

        return productQueryRepository.findByProductId(productId);
    }
}
