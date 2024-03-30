package com.stylelab.product.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.product.application.ProductDetails;
import com.stylelab.product.application.ProductService;
import com.stylelab.product.presentation.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> findByProductId(@PathVariable final Long productId) {

        ProductDetails productDetails = productService.findByProductId(productId);
        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        ProductDetailResponse.create(
                                productDetails.productDetail(), productDetails.productDetailImages()
                        )
                )
        );
    }
}
