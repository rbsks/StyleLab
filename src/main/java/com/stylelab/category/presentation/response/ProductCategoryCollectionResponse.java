package com.stylelab.category.presentation.response;

import com.stylelab.category.infrastructure.dto.ProductCategoryDto;
import com.stylelab.file.constant.ImageType;

import java.time.format.DateTimeFormatter;

public record ProductCategoryCollectionResponse(
        Long productId,
        Long storeId,
        String storeName,
        String productCategoryPath,
        String productCategoryName,
        String productMainImage,
        ImageType productMainImageType,
        String name,
        int price,
        int discountPrice,
        int discountRate,
        boolean soldOut,
        boolean deleted,
        String createdAt
) {

    public static ProductCategoryCollectionResponse create(ProductCategoryDto productCategoryDto) {
        return new ProductCategoryCollectionResponse(
                productCategoryDto.productId(),
                productCategoryDto.storeId(),
                productCategoryDto.storeName(),
                productCategoryDto.productCategoryPath(),
                productCategoryDto.productCategoryName(),
                productCategoryDto.productMainImage(),
                productCategoryDto.productMainImageType(),
                productCategoryDto.name(),
                productCategoryDto.price(),
                productCategoryDto.discountPrice(),
                productCategoryDto.discountRate(),
                productCategoryDto.soldOut(),
                productCategoryDto.deleted(),
                productCategoryDto.createdAt().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
    }
}
