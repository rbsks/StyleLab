package com.stylelab.storeproduct.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStoreProductRequest(
        ProductRequest productRequest,
        EntryMain entryMain,
        List<EntrySub> entrySubs,
        List<Description> descriptions,
        List<ProductOption1sRequest> productOption1sRequests) {

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
        
        @Override
        public String toString() {
            return """
                    ProductRequest {
                        storeId= %s,
                        productCategoryPath= %s,
                        name= %s,
                        price= %s,
                        discountRate= %s,
                        useOption= %s,
                        optionDepth= %s,
                        option1= %s,
                        option2= %s,
                        quantity= %s
                    }
                    """.formatted(storeId, productCategoryPath, name, productCategoryPath, discountRate, useOption, optionDepth, option1, option2, quantity);
        }
    }

    @Builder
    public record EntryMain(
            @NotBlank
            String entryMain
    ) {

        @Override
        public String toString() {
            return """
                    EntryMain {
                        entryMain= %s
                    }
                    """.formatted(entryMain);
        }
    }

    @Builder
    public record EntrySub(
            @NotBlank
            String entrySub
    ) {
        

        @Override
        public String toString() {
            return """
                    EntrySub {
                        entrySub= %s
                    }
                    """.formatted(entrySub);
        }
    }

    @Builder
    public record Description(
            @NotBlank
            String description
    ) {

        @Override
        public String toString() {
            return """
                    Description {
                        description= %s
                    }
                    """.formatted(description);
        }
    }

    @Builder
    public record ProductOption1sRequest(
            String option1Name,

            int quantity,

            int additionalPrice,

            List<ProductOption2sRequest> productOption2sRequests
    ) {
        
        @Override
        public String toString() {
            return """
                    ProductOption1sRequest {
                        option1Name= %s,
                        quantity= %s,
                        additionalPrice= %s,
                        productOption2sRequests= %s
                    }
                    """.formatted(option1Name, quantity, additionalPrice, productOption2sRequests);
        }
    }

    @Builder
    public record ProductOption2sRequest(
            String option2Name,

            int quantity,

            int additionalPrice
    ) {

        @Override
        public String toString() {
            return """
                    ProductOption2sRequest {
                        option2Name= %s,
                        quantity= %s,
                        additionalPrice= %s
                    }
                    """.formatted(option2Name, quantity, additionalPrice);
        }
    }

    @Override
    public String toString() {
        return """
                    CreateStoreProductRequest {
                        productRequest= %s,
                        entryMain= %s,
                        entrySubs= %s,
                        descriptions= %s,
                        productOption1sRequests= %s
                    }
                    """.formatted(productRequest, entryMain, entrySubs, descriptions, productOption1sRequests);
    }
}
