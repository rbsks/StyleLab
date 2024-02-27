package com.stylelab.product.repository;

import com.stylelab.product.repository.dto.ProductDetailImage;

import java.util.List;

public interface ProductImageQueryRepository {

    List<ProductDetailImage> findAllByProductId(final Long productId);
}
