package com.stylelab.category.infrastructure.dto;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import org.springframework.data.domain.Pageable;

public record ProductCategoryCondition(
        ProductCategoryType productCategoryType,
        Long productId,
        String productName,
        String productCategoryPath,
        Integer price1,
        Integer price2,
        Integer discountRate,
        Pageable pageable
) {

    public ProductCategoryCondition {
        if (productCategoryType == null) {
            throw new ProductCategoryException(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH);
        }
    }

    public static ProductCategoryCondition create(
            ProductCategoryType productCategoryType, Long productId, String productName, String productCategoryPath,
            Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        return new ProductCategoryCondition(
                productCategoryType, productId, productName, productCategoryPath, price1, price2, discountRate, pageable
        );
    }
}
