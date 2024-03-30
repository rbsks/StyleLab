package com.stylelab.product.domain;

import lombok.Builder;

@Builder
public record ProductOption2(
        Long productOption2Id,
        Long productOption1Id,
        String option2Name,
        int quantity,
        int additionalPrice,
        boolean soldOut,
        boolean deleted) {

    public ProductOption2 initProductOption1Id(Long productOption1Id) {
        return ProductOption2.builder()
                .productOption2Id(this.productOption2Id)
                .productOption1Id(productOption1Id)
                .option2Name(this.option2Name)
                .quantity(this.quantity)
                .additionalPrice(this.additionalPrice)
                .soldOut(this.soldOut)
                .deleted(this.deleted)
                .build();
    }
}
