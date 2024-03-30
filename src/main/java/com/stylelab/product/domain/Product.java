package com.stylelab.product.domain;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import lombok.Builder;

@Builder
public record Product(
        Long productId,
        Long storeId,
        String productCategoryPath,
        String name,
        int price,
        int discountPrice,
        int discountRate,
        boolean useOption,
        int optionDepth,
        String option1,
        String option2,
        int quantity,
        boolean soldOut,
        boolean deleted) {

    public Product calculateDiscountPrice() {
        if (this.discountRate < 0 || this.discountRate > 100) {
            throw new ProductException(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE, ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE.getMessage());
        }

        int discountedPrice = (int) (this.price * (double) this.discountRate / 100);
        int totalDiscountedPrice = (int) (Math.floor((double) (this.price - discountedPrice) / 1000) * 1000);

        return Product.builder()
                .productId(this.productId)
                .storeId(this.storeId)
                .productCategoryPath(this.productCategoryPath)
                .name(this.name)
                .price(this.price)
                .discountPrice(totalDiscountedPrice)
                .discountRate(this.discountRate)
                .useOption(this.useOption)
                .optionDepth(this.optionDepth)
                .option1(this.option1)
                .option2(this.option2)
                .quantity(this.quantity)
                .soldOut(this.soldOut)
                .deleted(this.deleted)
                .build();
    }
}
