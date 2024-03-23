package com.stylelab.product.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ProductOption1Detail1(
        Long productOption1Id,
        Long productId,
        String option1Name,
        int quantity,
        int additionalPrice,
        boolean soldOut,
        boolean deleted
) {

    @QueryProjection
    public ProductOption1Detail1(
            Long productOption1Id, Long productId, String option1Name, int quantity,
            int additionalPrice, boolean soldOut, boolean deleted) {
        this.productOption1Id = productOption1Id;
        this.productId = productId;
        this.option1Name = option1Name;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }
}
