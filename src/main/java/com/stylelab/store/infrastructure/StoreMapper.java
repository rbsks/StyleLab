package com.stylelab.store.infrastructure;

import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public StoreEntity toStoreEntity(
            String brand, String name, String address, String addressDetail,
            String postalCode, String businessLicenseNumber, ApproveType approveType) {

        return new StoreEntity(
                brand, name, address, addressDetail, postalCode, businessLicenseNumber, approveType
        );
    }

    public StoreEntity toStoreEntity(Store store) {

        return new StoreEntity(
                store.brand(), store.name(), store.address(), store.addressDetail(),
                store.postalCode(), store.businessLicenseNumber(), store.approveType()
        );
    }

    public StoreStaffEntity toStoreStaffEntity(
            StoreEntity storeEntity, String email, String encodePassword,
            String name, String nickname, String phoneNumber, StoreStaffRole storeStaffRole) {

        return new StoreStaffEntity(
                storeEntity, email, encodePassword, name, nickname, phoneNumber, storeStaffRole, false, null
        );
    }

    public StoreStaffEntity toStoreStaffEntity(StoreStaff storeStaff) {

        return new StoreStaffEntity(
                StoreEntity.createStore(storeStaff.store().storeId()), storeStaff.email(), storeStaff.password(), storeStaff.name(),
                storeStaff.nickname(), storeStaff.phoneNumber(), storeStaff.storeStaffRole(), storeStaff.withdrawal(), storeStaff.withdrawalAt()
        );
    }

    public Store toStore(StoreEntity storeEntity) {

        return new Store(
                storeEntity.getStoreId(), storeEntity.getBrand(), storeEntity.getName(), storeEntity.getAddress(),
                storeEntity.getAddressDetail(), storeEntity.getPostalCode(), storeEntity.getBusinessLicenseNumber(), storeEntity.getApproveType()
        );
    }

    public Store saveStore(
            String brand, String name, String address, String addressDetail,
            String postalCode, String businessLicenseNumber, ApproveType approveType) {

        return new Store(
                null, brand, name, address, addressDetail, postalCode, businessLicenseNumber, approveType
        );
    }

    public StoreStaff toStoreStaff(StoreStaffEntity storeStaffEntity) {

        return new StoreStaff(
                storeStaffEntity.getStoreStaffId(), this.toStore(storeStaffEntity.getStore()), storeStaffEntity.getEmail(),
                storeStaffEntity.getPassword(), storeStaffEntity.getName(), storeStaffEntity.getNickname(), storeStaffEntity.getPhoneNumber(),
                storeStaffEntity.getStoreStaffRole(), storeStaffEntity.isWithdrawal(), storeStaffEntity.getWithdrawalAt()
        );
    }

    public StoreStaff saveStoreStaff(
            Store store, String email, String encodePassword,
            String name, String nickname, String phoneNumber, StoreStaffRole storeStaffRole) {

        return new StoreStaff(
                null, store, email, encodePassword, name, nickname, phoneNumber, storeStaffRole, false, null
        );
    }
}
