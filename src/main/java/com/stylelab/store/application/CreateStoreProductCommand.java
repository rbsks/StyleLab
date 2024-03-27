package com.stylelab.store.application;

import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.presentation.request.CreateStoreProductRequest;

import java.util.Objects;

public record CreateStoreProductCommand(
        Long storeId,
        Long requestStoreId,
        CreateStoreProductRequest createStoreProductRequest) {

    public CreateStoreProductCommand {

        if (storeId == null || requestStoreId == null) {
            throw new StoreException(StoreError.BRAND_NAME_REQUIRE);
        }
    }

    public static CreateStoreProductCommand create(Long storeId, Long requestStoreId, CreateStoreProductRequest createStoreProductRequest) {

        if (!Objects.equals(storeId, requestStoreId)) {
            throw new StoreException(StoreError.FORBIDDEN_STORE);
        }


        return new CreateStoreProductCommand(storeId, requestStoreId, createStoreProductRequest);
    }
}
