package com.stylelab.store.application;

import com.stylelab.product.application.StoreProductFacade;
import com.stylelab.storage.application.StorageService;
import com.stylelab.storage.application.UploadResult;
import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.infrastructure.StoreMapper;
import com.stylelab.store.infrastructure.StoreRepository;
import com.stylelab.store.infrastructure.StoreStaffRepository;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.presentation.request.CreateStoreProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final StoreStaffRepository storeStaffRepository;
    private final StorageService storageService;
    private final StoreProductFacade storeProductFacade;
    private final PasswordEncoder passwordEncoder;
    
    public void applyStore(final ApplyStoreRequest applyStoreRequest) {

        ApplyStoreRequest.StoreRequest storeRequest = applyStoreRequest.store();
        ApplyStoreRequest.StoreStaffRequest storeStaffRequest = applyStoreRequest.storeStaff();

        if (!Objects.equals(storeStaffRequest.password(), storeStaffRequest.confirmPassword())) {
            throw new StoreException(StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT);
        }

        Store store = storeRepository.save(
                storeMapper.saveStore(
                        storeRequest.brand(), storeStaffRequest.name(), storeRequest.address(), storeRequest.addressDetail(),
                        storeRequest.postalCode(), storeRequest.businessLicenseNumber(), ApproveType.APPROVE
                )
        );

        String encodePassword = passwordEncoder.encode(storeStaffRequest.password());
        StoreStaff storeStaff = storeStaffRepository.save(
                storeMapper.saveStoreStaff(
                        store, storeStaffRequest.email(), encodePassword, storeStaffRequest.name(),
                        storeStaffRequest.nickname(), storeStaffRequest.phoneNumber(), StoreStaffRole.ROLE_STORE_OWNER
                )
        );
    }

    public UploadResult uploadMultipartFiles(UploadCommand uploadCommand) {

        return storageService.uploadMultipartFiles(uploadCommand.imageType(), uploadCommand.multipartFiles());
    }

    public Long createStoreProduct(CreateStoreProductCommand createStoreProductCommand) {

        return storeProductFacade.createStoreProduct(
                createStoreProductCommand.storeId(), 
                CreateStoreProductRequest.createStoreProductRequestVo(createStoreProductCommand.createStoreProductRequest())
        );
    }
}
