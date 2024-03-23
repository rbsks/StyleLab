package com.stylelab.product.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ProductOption1Detail2(
        Long productOption2Id,
        Long productId,
        String option2Name,
        int quantity,
        int additionalPrice,
        boolean soldOut,
        boolean deleted
) {

    @QueryProjection
    public ProductOption1Detail2(
            Long productOption2Id, Long productId, String option2Name, int quantity,
            int additionalPrice, boolean soldOut, boolean deleted) {
        this.productOption2Id = productOption2Id;
        this.productId = productId;
        this.option2Name = option2Name;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }
}
