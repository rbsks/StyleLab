package com.stylelab.category.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "product_categories")
public class ProductCategoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productCategoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private String categoryPath;

    private String parentCategory;
}
