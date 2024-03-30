package com.stylelab.store.presentation.response;

import com.stylelab.storage.application.UploadResult;

public record ImageUploadResponse(UploadResult result) {

    public static ImageUploadResponse create(UploadResult uploadResult) {
        return new ImageUploadResponse(uploadResult);
    }
}
