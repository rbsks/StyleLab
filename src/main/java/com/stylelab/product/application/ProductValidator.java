package com.stylelab.product.application;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.storage.constant.ImageType;
import com.stylelab.storeproduct.application.CreateStoreProductCommand;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class ProductValidator {

    public void validationCreateStoreProductRequest(CreateStoreProductCommand createStoreProductCommand) {

        validationProductMainImages(createStoreProductCommand.entryMain());
        validationProductSubImages(createStoreProductCommand.entrySubs());
        validationProductDescriptionImages(createStoreProductCommand.descriptions());
        validationProductInformation(createStoreProductCommand.productRequest());
        validationProductOptions(createStoreProductCommand);
    }

    private void validationProductMainImages(CreateStoreProductCommand.EntryMain entryMain) {
        if (entryMain == null || !StringUtils.hasText(entryMain.entryMain())) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_MAIN_REQUIRE);
        }
    }

    private void validationProductSubImages(List<CreateStoreProductCommand.EntrySub> entrySubs) {
        if (ObjectUtils.isEmpty(entrySubs)) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
        }
        if (entrySubs.size() > ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(
                            ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(),
                            ImageType.PRODUCT_ENTRY_SUB,
                            ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount()
                    )
            );
        }
        entrySubs.stream()
                .filter(entrySub -> !StringUtils.hasText(entrySub.entrySub()))
                .findAny()
                .ifPresent(entrySub -> {
                    throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
                });
    }

    private void validationProductDescriptionImages(List<CreateStoreProductCommand.Description> descriptions) {
        if (ObjectUtils.isEmpty(descriptions)) {
            throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
        }
        if (descriptions.size() > ImageType.PRODUCT_DESCRIPTION.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(
                            ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(),
                            ImageType.PRODUCT_DESCRIPTION,
                            ImageType.PRODUCT_DESCRIPTION.getMaxImageCount()
                    )
            );
        }
        descriptions.stream()
                .filter(description -> !StringUtils.hasText(description.description()))
                .findAny()
                .ifPresent(description -> {
                    throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
                });
    }

    private void validationProductInformation(CreateStoreProductCommand.ProductRequest productRequest) {
        if (productRequest == null) {
            throw new ProductException(ProductError.PRODUCT_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.productCategoryPath())) {
            throw new ProductException(ProductError.PRODUCT_CATEGORY_PATH_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.name())) {
            throw new ProductException(ProductError.PRODUCT_NAME_REQUIRE);
        }

        int minimumPrice  = 3_000;
        int maximumPrice = 1_000_000_000;
        int price = productRequest.price();
        if (price < minimumPrice || price > maximumPrice) {
            throw new ProductException(ProductError.PRODUCT_PRICE_OUT_OF_RANGE);
        }

        int minimumDiscountRate = 0;
        int maximumDiscountRate = 100;
        int discountRate = productRequest.discountRate();
        if (discountRate < minimumDiscountRate || discountRate > maximumDiscountRate) {
            throw new ProductException(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE);
        }
    }

    private void validationProductOptions(CreateStoreProductCommand createStoreProductCommand) {
        int minimumQuantity = 0;
        boolean useOption = createStoreProductCommand.getUseOption();
        if (!useOption) {
            int productQuantity = createStoreProductCommand.getQuantity();
            if (productQuantity < minimumQuantity) {
                throw new ProductException(ProductError.PRODUCT_QUANTITY_LESS_THEN_ZERO);
            }

            return;
        }

        int minimumOptionDepth = 0;
        int maximumOptionDepth = 2;
        int minimumOptionAdditionalPrice = 0;
        int maximumOptionAdditionalPrice = 100_000_000;
        int optionDepth = createStoreProductCommand.getOptionDepth();

        if (optionDepth <= minimumOptionDepth || optionDepth > maximumOptionDepth) {
            throw new ProductException(ProductError.PRODUCT_OPTION_DEPTH_OUT_OF_RANGE);
        }

        String option1 = createStoreProductCommand.getOption1Name();
        if (!StringUtils.hasText(option1)) {
            throw new ProductException(ProductError.PRODUCT_OPTION1_NAME_REQUIRE);
        }

        if (optionDepth == maximumOptionDepth) {
            String option2 = createStoreProductCommand.getOption2Name();
            if (!StringUtils.hasText(option2)) {
                throw new ProductException(ProductError.PRODUCT_OPTION2_NAME_REQUIRE);
            }
        }

        List<CreateStoreProductCommand.ProductOption1sRequest> productOption1sRequests = createStoreProductCommand.productOption1SRequest();
        validationProductOption1(optionDepth, minimumQuantity, minimumOptionAdditionalPrice, maximumOptionAdditionalPrice, productOption1sRequests);
    }

    private void validationProductOption1(
            int optionDepth, int minimumQuantity, int minimumOptionAdditionalPrice, int maximumOptionAdditionalPrice,
            List<CreateStoreProductCommand.ProductOption1sRequest> productOption1sRequests) {
        if (ObjectUtils.isEmpty(productOption1sRequests)) {
            throw new ProductException(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE);
        }

        for (CreateStoreProductCommand.ProductOption1sRequest productOption1sRequest : productOption1sRequests) {
            String option1Name = productOption1sRequest.option1Name();
            if (!StringUtils.hasText(option1Name)) {
                throw new ProductException(ProductError.OPTION1_NAME_REQUIRE);
            }

            int option1AdditionalPrice = productOption1sRequest.additionalPrice();
            if (option1AdditionalPrice < minimumOptionAdditionalPrice || option1AdditionalPrice > maximumOptionAdditionalPrice) {
                throw new ProductException(ProductError.OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE);
            }

            if (optionDepth == 1) {
                int option1Quantity = productOption1sRequest.quantity();
                if (option1Quantity < minimumQuantity) {
                    throw new ProductException(ProductError.OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
                }
                return;
            }

            List<CreateStoreProductCommand.ProductOption2sRequest> productOption2sRequests = productOption1sRequest.productOption2sRequest();
            validationProductOption2(minimumQuantity, minimumOptionAdditionalPrice, maximumOptionAdditionalPrice, productOption2sRequests);
        }
    }

    private void validationProductOption2(
            int minimumQuantity, int minimumOptionAdditionalPrice, int maximumOptionAdditionalPrice,
            List<CreateStoreProductCommand.ProductOption2sRequest> productOption2sRequests) {
        if (ObjectUtils.isEmpty(productOption2sRequests)) {
            throw new ProductException(ProductError.PRODUCT_OPTION2_REQUEST_REQUIRE);
        }

        for (CreateStoreProductCommand.ProductOption2sRequest productOption2sRequest : productOption2sRequests) {
            String option2Name = productOption2sRequest.option2Name();
            if (!StringUtils.hasText(option2Name)) {
                throw new ProductException(ProductError.OPTION2_NAME_REQUIRE);
            }

            int option2AdditionalPrice = productOption2sRequest.additionalPrice();
            if(option2AdditionalPrice < minimumOptionAdditionalPrice || option2AdditionalPrice > maximumOptionAdditionalPrice) {
                throw new ProductException(ProductError.OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE);
            }

            int option2Quantity = productOption2sRequest.quantity();
            if (option2Quantity < minimumQuantity) {
                throw new ProductException(ProductError.OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
            }
        }
    }
}
