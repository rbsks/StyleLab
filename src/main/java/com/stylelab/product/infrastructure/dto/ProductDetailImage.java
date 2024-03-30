package com.stylelab.product.infrastructure.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.stylelab.storage.constant.ImageType;

public record ProductDetailImage(
        Long productImageId,
        Long productId,
        String imageUrl,
        int imageOrder,
        ImageType imageType
) {

    @QueryProjection
    public ProductDetailImage(Long productImageId, Long productId, String imageUrl, int imageOrder, ImageType imageType) {
        this.productImageId = productImageId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
        this.imageType = imageType;
    }
}
