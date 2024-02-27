package com.stylelab.product.service;

import com.stylelab.product.repository.dto.ProductDetailImage;

import java.util.List;

public interface ProductImageService {

    List<ProductDetailImage> findAllByProductId(final Long productId);
}
