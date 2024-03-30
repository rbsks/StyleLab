package com.stylelab.storeproduct.application;

import com.stylelab.product.domain.Product;
import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.domain.ProductOption2;
import com.stylelab.storage.constant.ImageType;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.storeproduct.presentation.request.CreateStoreProductRequest;
import lombok.Builder;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public record CreateStoreProductCommand(
        Long storeId,
        Long requestStoreId,
        ProductRequest productRequest,
        EntryMain entryMain,
        List<EntrySub> entrySubs,
        List<Description> descriptions,
        List<ProductOption1sRequest> productOption1SRequest) {

    public CreateStoreProductCommand {
        if (!Objects.equals(storeId, requestStoreId)) {
            throw new StoreException(StoreError.FORBIDDEN_STORE);
        }
    }

    @Builder
    public record ProductRequest(
            Long storeId,
            String productCategoryPath,
            String name,
            int price,
            int discountRate,
            boolean useOption,
            int optionDepth,
            String option1,
            String option2,
            int quantity
    ) {


    }

    @Builder
    public record EntryMain(
            String entryMain
    ) {

    }

    @Builder
    public record EntrySub(
            String entrySub
    ) {

    }

    @Builder
    public record Description(
            String description
    ) {

    }

    @Builder
    public record ProductOption1sRequest(
            String option1Name,

            int quantity,

            int additionalPrice,

            List<ProductOption2sRequest> productOption2sRequest
    ) {

        public static ProductOption1 createProductOption1(Long productId, ProductOption1sRequest productOption1sRequest) {
            return ProductOption1.builder()
                    .productId(productId)
                    .option1Name(productOption1sRequest.option1Name)
                    .additionalPrice(productOption1sRequest.additionalPrice)
                    .quantity(productOption1sRequest.quantity)
                    .soldOut(false)
                    .deleted(false)
                    .build();
        }
    }

    @Builder
    public record ProductOption2sRequest(
            String option2Name,

            int quantity,

            int additionalPrice
    ) {

        public static ProductOption2 createProductOption2(ProductOption2sRequest productOption2sRequest) {
            return ProductOption2.builder()
                    .option2Name(productOption2sRequest.option2Name)
                    .additionalPrice(productOption2sRequest.additionalPrice)
                    .quantity(productOption2sRequest.quantity)
                    .soldOut(false)
                    .deleted(false)
                    .build();
        }
    }

    public Product createProduct() {
        return Product.builder()
                .productCategoryPath(this.productRequest.productCategoryPath)
                .storeId(this.productRequest.storeId)
                .name(this.productRequest.name)
                .price(this.productRequest.price)
                .discountRate(this.productRequest.discountRate)
                .useOption(this.productRequest.useOption)
                .optionDepth(this.productRequest.optionDepth)
                .option1(this.productRequest.option1)
                .option2(this.productRequest.option2)
                .quantity(this.productRequest.quantity)
                .build();
    }

    public List<ProductImage> createProductImages(Long productId) {
        List<ProductImage> productImages = new ArrayList<>();

        productImages.add(
                ProductImage.builder()
                        .productId(productId)
                        .imageUrl(this.entryMain.entryMain)
                        .imageType(ImageType.PRODUCT_ENTRY_MAIN)
                        .build()
        );

        for (EntrySub entrySub : this.entrySubs) {
            productImages.add(
                    ProductImage.builder()
                            .productId(productId)
                            .imageUrl(entrySub.entrySub)
                            .imageType(ImageType.PRODUCT_ENTRY_SUB)
                            .build()
            );
        }

        for (Description description : this.descriptions) {
            productImages.add(
                    ProductImage.builder()
                            .productId(productId)
                            .imageUrl(description.description)
                            .imageType(ImageType.PRODUCT_DESCRIPTION)
                            .build()
            );
        }

        return productImages;
    }

    public List<ProductOption1> createProductOptions(Long productId) {

        List<ProductOption1> productOption1s = new ArrayList<>();

        for (ProductOption1sRequest productOption1sRequest : this.productOption1SRequest) {
            productOption1s.add(
                    ProductOption1.builder()
                            .productId(productId)
                            .option1Name(productOption1sRequest.option1Name)
                            .additionalPrice(productOption1sRequest.additionalPrice)
                            .quantity(productOption1sRequest.quantity)
                            .productOption2s(
                                    productOption1sRequest.productOption2sRequest != null ?
                                            productOption1sRequest.productOption2sRequest.stream()
                                                    .map(productOption2sRequest ->
                                                            ProductOption2.builder()
                                                                    .option2Name(productOption2sRequest.option2Name)
                                                                    .quantity(productOption2sRequest.quantity)
                                                                    .additionalPrice(productOption2sRequest.additionalPrice)
                                                                    .build()
                                                    )
                                                    .toList()
                                            :
                                            null
                            )
                            .build()
            );
        }

        return productOption1s;
    }

    public static CreateStoreProductCommand create(Long storeId, Long requestStoreId, CreateStoreProductRequest createStoreProductRequest) {

        return new CreateStoreProductCommand(
                storeId,
                requestStoreId,
                new ProductRequest(
                        createStoreProductRequest.productRequest().storeId(),
                        createStoreProductRequest.productRequest().productCategoryPath(),
                        createStoreProductRequest.productRequest().name(),
                        createStoreProductRequest.productRequest().price(),
                        createStoreProductRequest.productRequest().discountRate(),
                        createStoreProductRequest.productRequest().useOption(),
                        createStoreProductRequest.productRequest().optionDepth(),
                        createStoreProductRequest.productRequest().option1(),
                        createStoreProductRequest.productRequest().option2(),
                        createStoreProductRequest.productRequest().quantity()
                ),
                new EntryMain(createStoreProductRequest.entryMain().entryMain()),
                createStoreProductRequest.entrySubs().stream()
                        .map(entrySub -> {
                            return new EntrySub(entrySub.entrySub());
                        })
                        .toList(),
                createStoreProductRequest.descriptions().stream()
                        .map(description -> {
                            return new Description(description.description());
                        })
                        .toList(),
                !ObjectUtils.isEmpty(createStoreProductRequest.productOption1sRequests()) ?
                        createStoreProductRequest.productOption1sRequests().stream()
                                .map(productOption1sRequest -> {
                                    return new ProductOption1sRequest(
                                            productOption1sRequest.option1Name(),
                                            productOption1sRequest.quantity(),
                                            productOption1sRequest.additionalPrice(),
                                            !ObjectUtils.isEmpty(productOption1sRequest.productOption2sRequests()) ?
                                                    productOption1sRequest.productOption2sRequests().stream()
                                                            .map(productOption2sRequest -> {
                                                                return new ProductOption2sRequest(
                                                                        productOption2sRequest.option2Name(),
                                                                        productOption2sRequest.quantity(),
                                                                        productOption2sRequest.additionalPrice()
                                                                );
                                                            })
                                                            .toList()
                                                    : null
                                    );
                                })
                                .toList()
                        : null
        );
    }

    public boolean getUseOption() {
        return this.productRequest.useOption;
    }

    public int getQuantity() {
       return this.productRequest.quantity;
    }

    public int getOptionDepth() {
        return this.productRequest.optionDepth;
    }

    public String getOption1Name() {
        return this.productRequest.option1;
    }

    public String getOption2Name() {
        return this.productRequest.option2;
    }
}
