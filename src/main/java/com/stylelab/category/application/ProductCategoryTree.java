package com.stylelab.category.application;

import com.stylelab.category.domain.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductCategoryTree implements Serializable {

    private String categoryName;
    private String categoryPath;
    private String parentCategory;
    private List<ProductCategoryTree> childCategories;

    public static ProductCategoryTree create(ProductCategory productCategory) {
        return new ProductCategoryTree(
                productCategory.categoryName(),
                productCategory.categoryPath(),
                productCategory.parentCategory(),
                new ArrayList<>()
        );
    }

    public void addAllChildCategories(List<ProductCategoryTree> childCategories) {
        this.childCategories.addAll(childCategories);
    }
}
