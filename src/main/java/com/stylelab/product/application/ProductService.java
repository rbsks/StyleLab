package com.stylelab.product.application;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.infrastructure.ProductImageRepository;
import com.stylelab.product.infrastructure.ProductRepository;
import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public ProductDetails findByProductId(Long productId) {
        ProductDetail productDetail = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductException(ProductError.NOT_FOUND_PRODUCT));;
        List<ProductDetailImage> productDetailImages = productImageRepository.findAllByProductId(productId);

        return ProductDetails.create(productDetail, productDetailImages);
    }
}
