package com.stylelab.product.domain;

import com.stylelab.storage.constant.ImageType;
import lombok.Builder;

@Builder
public record ProductImage(
        Long productImageId,
        Long productId,
        String imageUrl,
        int imageOrder,
        ImageType imageType) {

}
