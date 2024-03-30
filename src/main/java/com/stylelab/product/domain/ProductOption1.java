package com.stylelab.product.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductOption1(
        Long productOption1Id,
        Long productId,
        String option1Name,
        int quantity,
        int additionalPrice,
        boolean soldOut,
        boolean deleted,
        List<ProductOption2> productOption2s) {

}
