package com.stylelab.category.domain.pants;

import com.stylelab.category.domain.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "product_category_001002")
public class PantsProductCategory extends ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_001002_id", nullable = false)
    private Long id;

}
