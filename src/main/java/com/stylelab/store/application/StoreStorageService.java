package com.stylelab.store.application;

import com.stylelab.storage.application.StorageService;
import com.stylelab.storage.application.UploadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreStorageService {

    private final StorageService storageService;


    public UploadResult uploadMultipartFiles(UploadCommand uploadCommand) {

        return storageService.uploadMultipartFiles(uploadCommand.imageType(), uploadCommand.multipartFiles());
    }

}
