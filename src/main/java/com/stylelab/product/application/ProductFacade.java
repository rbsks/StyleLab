package com.stylelab.product.application;

import com.stylelab.common.dto.PagingResponse;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.presentation.response.ProductCollectionResponse;
import com.stylelab.product.presentation.response.ProductDetailResponse;
import com.stylelab.product.presentation.response.ProductOptionDetailResponse;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.repository.dto.ProductDetailImage;
import com.stylelab.product.service.ProductImageService;
import com.stylelab.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final ProductImageService productImageService;

    public PagingResponse<ProductCollectionResponse> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        return PagingResponse.createOffSetPagingResponse(
                productService.findByProductByConditions(productName, productCategoryPath, price1, price2, discountRate, pageable)
                        .map(ProductCollectionResponse::createProductCollectionResponse)
        );
    }

    public ProductDetailResponse findByProductId(final Long productId) {
        ProductDetail productDetail = productService.findByProductId(productId);

        if (productDetail.deleted()) {
            throw new ProductException(ProductError.ALREADY_DELETED_PRODUCT);
        }

        List<ProductDetailImage> productDetailImages = productImageService.findAllByProductId(productId);

        return ProductDetailResponse.createProductDetailResponse(productDetail, productDetailImages);
    }

    public ProductOptionDetailResponse findByProductOption(final Long productId) {

        // TODO get product option 1

        // TODO get product option 2

        // TODO aggregate product 1, product 2

        return null;
    }
}
