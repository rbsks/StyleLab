package com.stylelab.product.infrastructure;

import com.stylelab.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Entity(name = "product_option_2")
public class ProductOption2Entity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option2_id", nullable = false)
    private Long productOption2Id;

    @Column(name = "product_option1_id")
    private Long productOption1Id;

    @Column(name = "option2_name", nullable = false)
    private String option2Name;

    private int quantity;

    private int additionalPrice;

    private boolean soldOut;

    private boolean deleted;

}
