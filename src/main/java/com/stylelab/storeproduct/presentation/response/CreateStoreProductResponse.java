package com.stylelab.storeproduct.presentation.response;

public record CreateStoreProductResponse(Long productId) {

    public static CreateStoreProductResponse create(Long productId) {
        return new CreateStoreProductResponse(productId);
    }
}
