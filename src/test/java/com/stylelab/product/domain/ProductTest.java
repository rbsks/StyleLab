package com.stylelab.product.domain;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductTest {

    @Test
    @DisplayName("상품 도메인 할인율 계산 - 할인율이 1 ~ 99 인 경우 할인 가격 계산에 성공한다.")
    public void successCalculateDiscountPrice() throws Exception {
        //given
        int discountRate = 10;
        Product product = createProductOfDiscountRate(discountRate);

        //when
        Product calculateDiscountPrice = product.calculateDiscountPrice();

        //then
        assertThat(calculateDiscountPrice.discountPrice()).isEqualTo(90000);
    }


    @Test
    @DisplayName("상품 도메인 할인율 계산 - 할인율이 0 보다 작은 경우 할인 가격 계산에 실패한다.")
    public void failCalculateDiscountPrice_01() throws Exception {
        //given
        int discountRate = -1;
        Product product = createProductOfDiscountRate(discountRate);

        //when
        Throwable throwable = catchThrowable(product::calculateDiscountPrice);

        //then
        assertThat(throwable).isInstanceOf(ProductException.class);
        assertThat(throwable.getMessage()).isEqualTo(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE.getMessage());
    }

    @Test
    @DisplayName("상품 도메인 할인율 계산 - 할인율이 100 보다 큰 경우 할인 가격 계산에 실패한다.")
    public void failCalculateDiscountPrice_02() throws Exception {
        //given
        int discountRate = 101;
        Product product = createProductOfDiscountRate(discountRate);

        //when
        Throwable throwable = catchThrowable(product::calculateDiscountPrice);

        //then
        assertThat(throwable).isInstanceOf(ProductException.class);
        assertThat(throwable.getMessage()).isEqualTo(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE.getMessage());
    }

    private Product createProductOfDiscountRate(int discountRate) {
        return Product.builder()
                .productId(1L)
                .storeId(1L)
                .productCategoryPath(ProductCategoryType.SHOES.getProductCategoryPath())
                .name("shoes")
                .price(100000)
                .discountRate(discountRate)
                .useOption(false)
                .quantity(10)
                .build();
    }
}
