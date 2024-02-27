package com.stylelab.product.repository;

import com.stylelab.product.repository.dto.ProductCollection;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.repository.dto.ProductDetailImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {

    Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable);

    Optional<ProductDetail> findByProductId(final Long productId);
    List<ProductDetailImage> findAllByProductId(final Long productId);
}
