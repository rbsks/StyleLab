package com.stylelab.product.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ProductDetail(
    Long productId,
    Long storeId,
    String storeName,
    String storeBrand,
    String categoryPath,
    String categoryName,
    String productName,
    int price,
    int discountPrice,
    int discountRate,
    boolean useOption,
    int optionDepth,
    String option1,
    String option2,
    int quantity,
    boolean soldOut,
    boolean deleted
) {

    @QueryProjection
    public ProductDetail(
            Long productId, Long storeId, String storeName, String storeBrand, String categoryPath, String categoryName,
            String productName, int price, int discountPrice, int discountRate, boolean useOption, int optionDepth,
            String option1, String option2, int quantity, boolean soldOut, boolean deleted) {
        this.productId = productId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeBrand = storeBrand;
        this.categoryPath = categoryPath;
        this.categoryName = categoryName;
        this.productName = productName;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.useOption = useOption;
        this.optionDepth = optionDepth;
        this.option1 = option1;
        this.option2 = option2;
        this.quantity = quantity;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }
}
