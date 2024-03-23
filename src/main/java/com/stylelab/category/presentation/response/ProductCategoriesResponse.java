package com.stylelab.category.presentation.response;

import com.stylelab.category.application.ProductCategoryTree;

import java.util.List;

public record ProductCategoriesResponse(List<ProductCategoryTree> categories) {

  public static ProductCategoriesResponse create(List<ProductCategoryTree> categories) {
      return new ProductCategoriesResponse(categories);
  }
}
