package com.stylelab.store.application;

import com.stylelab.storage.constant.ImageType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public record UploadCommand(
        Long storeId,
        Long requestStoreId,
        String email,
        StoreStaffRole storeStaffRole,
        ImageType imageType,
        List<MultipartFile> multipartFiles) {

    public UploadCommand {

        if (storeId == null || requestStoreId == null) {
            throw new StoreException(StoreError.BRAND_NAME_REQUIRE);
        }
    }

    public static UploadCommand create(
            Long storeId, Long requestStoreId, String email, StoreStaffRole storeStaffRole, ImageType imageType, List<MultipartFile> multipartFiles) {

        if (!Objects.equals(storeId, requestStoreId)) {
            throw new StoreException(StoreError.FORBIDDEN_STORE);
        }

        return new UploadCommand(storeId, requestStoreId, email, storeStaffRole, imageType, multipartFiles);
    }
}
