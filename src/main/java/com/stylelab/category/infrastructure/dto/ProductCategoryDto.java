package com.stylelab.category.infrastructure.dto;

import com.stylelab.storage.constant.ImageType;

import java.time.LocalDateTime;

public record ProductCategoryDto(
        Long productCategoryId,
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
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
