package com.stylelab.product.service;

import com.stylelab.product.repository.ProductImageQueryRepository;
import com.stylelab.product.repository.dto.ProductDetailImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageQueryRepository productImageQueryRepository;

    @Override
    public List<ProductDetailImage> findAllByProductId(final Long productId) {
        return productImageQueryRepository.findAllByProductId(productId);
    }
}
