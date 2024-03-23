package com.stylelab.category.domain;

public record ProductCategory(
        Long productCategoryId,
        String categoryName,
        String categoryPath,
        String parentCategory){

}
