package com.stylelab.store.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.storage.constant.ImageType;
import com.stylelab.storage.exception.StorageError;
import com.stylelab.store.application.CreateStoreCommand;
import com.stylelab.store.application.CreateStoreStaffCommand;
import com.stylelab.store.application.StoreService;
import com.stylelab.store.application.StoreStorageService;
import com.stylelab.store.application.UploadCommand;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.presentation.response.ImageUploadResponse;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreStorageService storageService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Void>> applyStore(@RequestBody @Valid final ApplyStoreRequest applyStoreRequest) {
        CreateStoreCommand createStoreCommand = CreateStoreCommand.create(
                applyStoreRequest.store().brand(), applyStoreRequest.store().name(), applyStoreRequest.store().address(),
                applyStoreRequest.store().addressDetail(), applyStoreRequest.store().postalCode(), applyStoreRequest.store().businessLicenseNumber()
        );
        CreateStoreStaffCommand createStoreStaffCommand = CreateStoreStaffCommand.create(
                applyStoreRequest.storeStaff().email(), applyStoreRequest.storeStaff().password(),
                applyStoreRequest.storeStaff().confirmPassword(), applyStoreRequest.storeStaff().name(),
                applyStoreRequest.storeStaff().nickname(), applyStoreRequest.storeStaff().phoneNumber()
        );

        storeService.applyStore(createStoreCommand, createStoreStaffCommand);
        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.CREATED);
    }

    @PostMapping("/{storeId}/products/images/{imageType}")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadMultipartFiles(
            @AuthenticationPrincipal StorePrincipal storePrincipal,
            @NotNull(message = "STORE_ID_REQUIRE", payload = StoreError.class)
            @PathVariable(name = "storeId") final Long storeId,
            @NotNull(message = "IMAGE_TYPE_REQUIRE", payload = StorageError.class)
            @PathVariable(name = "imageType") final ImageType imageType,
            @RequestPart(name = "files", required = false) final List<MultipartFile> multipartFiles) {

        UploadCommand uploadCommand = UploadCommand.create(
                storePrincipal.getStoreId(), storeId, storePrincipal.getEmail(),
                storePrincipal.getStoreStaffRole(), imageType, multipartFiles
        );

        return new ResponseEntity<>(
                ApiResponse.createApiResponse(
                        ImageUploadResponse.create(
                                storageService.uploadMultipartFiles(uploadCommand)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
