package com.stylelab.product.application;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 상세 조회 성공")
    public void successFindByProductId() throws Exception {
        //given
        Long productId = 60L;

        //when
        ProductDetails productDetails = productService.findByProductId(productId);
        ProductDetail productDetail = productDetails.productDetail();
        List<ProductDetailImage> productDetailImages = productDetails.productDetailImages();

        //then
        assertThat(productDetail.productId()).isEqualTo(productId);
        for (ProductDetailImage productDetailImage : productDetailImages) {
            assertThat(productDetailImage.productId()).isEqualTo(productId);
        }
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 존재하지 않거나, 삭제된 상품인 경우 상품 상세 조회에 실패한다.")
    public void failFindByProductId() throws Exception {
        //given
        Long productId = 1L;

        //when
        Throwable throwable = catchThrowable(() ->
                productService.findByProductId(productId));

        //then
        assertThat(throwable).isInstanceOf(ProductException.class);
        assertThat(throwable.getMessage()).isEqualTo(ProductError.NOT_FOUND_PRODUCT.getMessage());
    }
}
