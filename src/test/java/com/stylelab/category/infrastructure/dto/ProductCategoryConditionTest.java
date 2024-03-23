package com.stylelab.category.infrastructure.dto;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductCategoryConditionTest {

    private ProductCategoryType productCategoryType;
    private Long productId;
    private String productName;
    private String productCategoryPath;
    private Integer price1;
    private Integer price2;
    private Integer discountRate;
    private Pageable pageable;

    @Test
    @DisplayName("ProductCategoryType이 null인 경우 ProductCategoryCondition 객체 생성에 실패한다.")
    public void failCreateProductCategoryCondition() throws Exception {
        //given


        //when
        Throwable throwable = catchThrowable(() ->
                ProductCategoryCondition.create(
                        productCategoryType, productId, productName, productCategoryPath,
                        price1, price2, discountRate, pageable
                )
        );

        //then
        assertThat(throwable).isInstanceOf(ProductCategoryException.class);
        assertThat(throwable.getMessage()).isEqualTo(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH.getMessage());
    }

    @Test
    @DisplayName("ProductCategoryCondition 객체 생성에 성공한다.")
    public void successCreateProductCategoryCondition() throws Exception {
        //given
        productCategoryType = ProductCategoryType.SNEAKERS;
        productId = 1L;
        productName = "스니커즈";
        productCategoryPath = ProductCategoryType.SNEAKERS.getProductCategoryPath();
        price1 = 10000;
        price2 = 13000;
        discountRate = 10;
        pageable = PageRequest.of(1, 10);

        //when
        ProductCategoryCondition productCategoryCondition = ProductCategoryCondition.create(
                productCategoryType, productId, productName, productCategoryPath,
                price1, price2, discountRate, pageable
        );

        //then
        assertThat(productCategoryCondition.productCategoryType()).isEqualTo(productCategoryType);
        assertThat(productCategoryCondition.productId()).isEqualTo(productId);
        assertThat(productCategoryCondition.productName()).isEqualTo(productName);
        assertThat(productCategoryCondition.productCategoryPath()).isEqualTo(productCategoryPath);
        assertThat(productCategoryCondition.price1()).isEqualTo(price1);
        assertThat(productCategoryCondition.price2()).isEqualTo(price2);
        assertThat(productCategoryCondition.discountRate()).isEqualTo(discountRate);
        assertThat(productCategoryCondition.pageable().getOffset()).isEqualTo(pageable.getOffset());
        assertThat(productCategoryCondition.pageable().getPageSize()).isEqualTo(pageable.getPageSize());
    }
}
