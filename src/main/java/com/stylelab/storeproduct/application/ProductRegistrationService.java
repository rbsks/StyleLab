package com.stylelab.storeproduct.application;

import com.stylelab.product.application.ProductValidator;
import com.stylelab.product.domain.Product;
import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.infrastructure.ProductImageRepository;
import com.stylelab.product.infrastructure.ProductOption1Repository;
import com.stylelab.product.infrastructure.ProductOption2Repository;
import com.stylelab.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRegistrationService {

    private final ProductValidator productValidator;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOption1Repository productOption1Repository;
    private final ProductOption2Repository productOption2Repository;

    public Long createStoreProduct(CreateStoreProductCommand createStoreProductCommand) {

        productValidator.validationCreateStoreProductRequest(createStoreProductCommand);

        Product product = productRepository.save(
                createStoreProductCommand
                        .createProduct()
                        .calculateDiscountPrice()
        );

        List<ProductImage> productImages = productImageRepository.saveAll(
                createStoreProductCommand.createProductImages(product.productId())
        );

        if (createStoreProductCommand.getUseOption()) {

            List<ProductOption1> productOption1s = createStoreProductCommand.createProductOptions(product.productId());

            for (ProductOption1 productOption1 : productOption1s) {
                ProductOption1 saveProductOption1 = productOption1Repository.save(productOption1);
                if (productOption1.productOption2s() != null) {
                    productOption2Repository.saveAll(
                            productOption1.productOption2s().stream()
                                    .map(productOption2 -> productOption2.initProductOption1Id(saveProductOption1.productOption1Id()))
                                    .toList()
                    );
                }
            }

        }

        return product.productId();
    }
}
