package com.stylelab.storeproduct.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.store.exception.StoreError;
import com.stylelab.storeproduct.application.CreateStoreProductCommand;
import com.stylelab.storeproduct.application.ProductRegistrationService;
import com.stylelab.storeproduct.presentation.request.CreateStoreProductRequest;
import com.stylelab.storeproduct.presentation.response.CreateStoreProductResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreProductController {

    private final ProductRegistrationService productRegistrationService;

    @PostMapping("/{storeId}/products")
    public ResponseEntity<ApiResponse<CreateStoreProductResponse>> createStoreProduct(
            @AuthenticationPrincipal StorePrincipal storePrincipal,
            @NotNull(message = "STORE_ID_REQUIRE", payload = StoreError.class)
            @PathVariable(name = "storeId") final Long storeId,
            @RequestBody final CreateStoreProductRequest createStoreProductRequest) {

        CreateStoreProductCommand createStoreProductCommand = CreateStoreProductCommand.create(
                storePrincipal.getStoreId(),
                storeId,
                createStoreProductRequest
        );

        return new ResponseEntity<>(
                ApiResponse.createApiResponse(
                        CreateStoreProductResponse.create(
                                productRegistrationService.createStoreProduct(
                                        createStoreProductCommand
                                )
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
