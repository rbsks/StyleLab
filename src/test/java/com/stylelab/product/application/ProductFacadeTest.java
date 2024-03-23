package com.stylelab.product.application;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.service.ProductImageService;
import com.stylelab.product.service.ProductService;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductFacadeTest {

    @Mock
    private ProductService productService;
    @Mock
    private ProductImageService productImageService;

    @InjectMocks
    private ProductFacade productFacade;

    @Nested
    @DisplayName("상품 상세 조회 테스트")
    public class FindByProductIdTest {

        @Test
        @DisplayName("상품 상세 조회 실패 - 삭제된 상품인 경우 상품 상세 조회에 실패한다.")
        public void failureFindByProductId() throws Exception {
            //given
            final Long productId = 1L;
            given(productService.findByProductId(anyLong()))
                    .willReturn(createDeletedProductDetail());

            //when
            AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert =
                    assertThatThrownBy(() -> productFacade.findByProductId(productId));

            //then
            abstractThrowableAssert
                    .isInstanceOf(ProductException.class);
            abstractThrowableAssert
                    .hasMessageContaining(ProductError.ALREADY_DELETED_PRODUCT.getMessage());
        }

        private ProductDetail createDeletedProductDetail() {
            return new ProductDetail(
                    1L,
                    1L,
                    "coby brand",
                    "coby",
                    ProductCategoryType.HOODIE.getProductCategoryPath(),
                    ProductCategoryType.HOODIE.getProductCategoryName(),
                    "후드티",
                    500000,
                    50000,
                    0,
                    false,
                    0,
                    null,
                    null,
                    1000,
                    false,
                    true
            );
        }
    }
}
