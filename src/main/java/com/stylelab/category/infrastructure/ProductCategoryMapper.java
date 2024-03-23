package com.stylelab.category.infrastructure;

import com.stylelab.category.domain.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    public ProductCategory toProductCategories(ProductCategoryEntity productCategoryEntity) {
        return new ProductCategory(
                productCategoryEntity.getProductCategoryId(),
                productCategoryEntity.getCategoryName(),
                productCategoryEntity.getCategoryPath(),
                productCategoryEntity.getParentCategory()
        );
    }
}
