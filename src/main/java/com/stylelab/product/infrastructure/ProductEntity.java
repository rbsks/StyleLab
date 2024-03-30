package com.stylelab.product.infrastructure;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.store.infrastructure.StoreEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Entity(name = "products")
public class ProductEntity extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productId;

    private Long storeId;

    @Column(nullable = false)
    private String productCategoryPath;

    @Column(nullable = false)
    private String name;

    private int price;

    private int discountPrice;

    private int discountRate;

    private boolean useOption;

    private int optionDepth;

    private String option1;

    private String option2;

    private int quantity;

    private boolean soldOut;

    private boolean deleted;
}
