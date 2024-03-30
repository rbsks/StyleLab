package com.stylelab.category.application;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import org.springframework.data.domain.Pageable;

public record ProductCategoryQueryCriteria(
        Long productId,
        String productName,
        String productCategoryPath,
        Integer price1,
        Integer price2,
        Integer discountRate,
        ProductCategoryType productCategoryType,
        Pageable pageable) {

    public static ProductCategoryQueryCriteria create(
            Long productId, String productName, String productCategoryPath,
            Integer price1, Integer price2, Integer discountRate, Pageable pageable) {

        ProductCategoryType productCategoryType = ProductCategoryType.of(productCategoryPath)
                .orElseThrow(() ->  new ProductCategoryException(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH));

        return new ProductCategoryQueryCriteria(
                productId, productName, productCategoryPath, price1, price2, discountRate, productCategoryType, pageable
        );
    }
}
