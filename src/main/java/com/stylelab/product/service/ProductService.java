package com.stylelab.product.service;

import com.stylelab.product.repository.dto.ProductCollection;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.repository.dto.ProductDetailImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable);

    ProductDetail findByProductId(final Long productId);
}
