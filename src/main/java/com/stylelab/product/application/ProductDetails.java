package com.stylelab.product.application;

import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;

import java.util.List;

public record ProductDetails(
        ProductDetail productDetail,
        List<ProductDetailImage> productDetailImages
) {

    public static ProductDetails create(ProductDetail productDetail, List<ProductDetailImage> productDetailImages) {
        return new ProductDetails(productDetail, productDetailImages);
    }
}
