package com.stylelab.category.application;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductCategoryQueryCriteriaTest {

    private Long productId;
    private String productName;
    private String productCategoryPath;
    private Integer price1;
    private Integer price2;
    private Integer discountRate;
    private Pageable pageable;

    @Test
    @DisplayName("productCategoryPath이 유효하지 않은 경우 ProductCategoryQueryCriteria 객체 생성에 실패한다.")
    public void failProductCategoryQueryCriteria() throws Exception {
        //given
        productCategoryPath = "KEY_BOARD";

        //when
        Throwable throwable = catchThrowable(() ->
                ProductCategoryQueryCriteria.create(
                        productId, productName, productCategoryPath, price1, price2, discountRate, pageable
                )
        );

        //then
        assertThat(throwable).isInstanceOf(ProductCategoryException.class);
        assertThat(throwable.getMessage()).isEqualTo(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH.getMessage());
    }

    @Test
    @DisplayName("ProductCategoryQueryCriteria 객체 생성에 성공한다.")
    public void successProductCategoryQueryCriteria() throws Exception {
        //given
        productId = 1L;
        productName = "스니커즈";
        productCategoryPath = ProductCategoryType.SNEAKERS.getProductCategoryPath();
        price1 = 10000;
        price2 = 13000;
        discountRate = 10;
        pageable = PageRequest.of(1, 10);

        //when
        ProductCategoryQueryCriteria productCategoryQueryCriteria = ProductCategoryQueryCriteria.create(
                productId, productName, productCategoryPath, price1, price2, discountRate, pageable
        );
        
        //then
        assertThat(productCategoryQueryCriteria.productCategoryType()).isEqualTo(ProductCategoryType.SNEAKERS);
        assertThat(productCategoryQueryCriteria.productId()).isEqualTo(productId);
        assertThat(productCategoryQueryCriteria.productName()).isEqualTo(productName);
        assertThat(productCategoryQueryCriteria.productCategoryPath()).isEqualTo(productCategoryPath);
        assertThat(productCategoryQueryCriteria.price1()).isEqualTo(price1);
        assertThat(productCategoryQueryCriteria.price2()).isEqualTo(price2);
        assertThat(productCategoryQueryCriteria.discountRate()).isEqualTo(discountRate);
        assertThat(productCategoryQueryCriteria.pageable().getOffset()).isEqualTo(pageable.getOffset());
        assertThat(productCategoryQueryCriteria.pageable().getPageSize()).isEqualTo(pageable.getPageSize());
    }
}
