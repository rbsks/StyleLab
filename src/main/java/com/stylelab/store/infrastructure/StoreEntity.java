package com.stylelab.store.infrastructure;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.store.constant.ApproveType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Entity(name = "stores")
public class StoreEntity extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String businessLicenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApproveType approveType;

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST)
    List<StoreStaffEntity> storeStaffs = new ArrayList<>();

    private StoreEntity(Long storeId) {
        this.storeId = storeId;
    }

    public void addStoreStaff(StoreStaffEntity storeStaff) {
        storeStaff.addStore(this);
        this.storeStaffs.add(storeStaff);
    }

    public static StoreEntity of(Long storeId) {
        return new StoreEntity(storeId);
    }
}
