package com.stylelab.category.application;

import com.stylelab.category.infrastructure.dto.ProductCategoryDto;
import org.springframework.data.domain.Slice;

public record ProductCategoryCollection(
        Slice<ProductCategoryDto> productCategoryDtos,
        Long nextToken) {

    public static ProductCategoryCollection create(Slice<ProductCategoryDto> productCategoryDtos, Long nextToken) {
        return new ProductCategoryCollection(productCategoryDtos, nextToken);
    }
}
