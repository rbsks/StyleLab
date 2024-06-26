package com.stylelab.storage.application;

import org.springframework.web.multipart.MultipartFile;

public record UploadFile(
        MultipartFile multipartFile,
        String uploadFilename
) {

    public static UploadFile createUploadFile(MultipartFile multipartFile, String uploadFilename) {
        return new UploadFile(multipartFile, uploadFilename);
    }
}
