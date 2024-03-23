package com.stylelab.category.application;

import com.stylelab.category.domain.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductCategoryTreeTest {

    private List<ProductCategory> productCategories;

    @BeforeEach
    public void init() {
        productCategories = Arrays.asList(
                new ProductCategory(1L,"상의", "001001", null),
                new ProductCategory(2L, "맨투맨/후드", "001001001", "001001"),
                new ProductCategory(3L, "맨투맨", "001001001001", "001001001"),
                new ProductCategory(4L, "후드", "001001001002", "001001001"),
                new ProductCategory(5L, "니트/스웨터", "001001002", "001001"),
                new ProductCategory(6L, "니트", "001001002001", "001001002"),
                new ProductCategory(7L, "스웨터", "001001002002", "001001002"),
                new ProductCategory(8L, "반소매 티셔츠", "001001003", "001001"),
                new ProductCategory(9L, "셔츠", "001001004", "001001"),
                new ProductCategory(10L, "바지", "001002", null),
                new ProductCategory(11L, "데님 팬츠", "001002001", "001002"),
                new ProductCategory(12L, "코튼 팬츠", "001002002", "001002"),
                new ProductCategory(13L, "슬랙스", "001002003", "001002")
        );
    }

    @Test
    @DisplayName("카테고리 트리를 재귀적으로 생성합니다.")
    public void generateCategoryTreeRecursively() {

        List<ProductCategoryTree> parentCategories = productCategories.stream()
                .filter(productCategory -> !StringUtils.hasText(productCategory.parentCategory()))
                .map(ProductCategoryTree::create)
                .toList();

        generateCategoryTreeRecursively(productCategories, parentCategories);

        assertEquals(2, parentCategories.size());
        assertEquals(4, parentCategories.get(0).getChildCategories().size());
        assertEquals(2, parentCategories.get(0).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(0).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(0).getChildCategories().get(1).getChildCategories().size());
        assertEquals(2, parentCategories.get(0).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(1).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(1).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(2).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(3).getChildCategories().size());
        assertEquals(3, parentCategories.get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(2).getChildCategories().size());
    }

    private static void generateCategoryTreeRecursively(List<ProductCategory> productCategories, List<ProductCategoryTree> parentCategories) {

        for (ProductCategoryTree parentCategory : parentCategories) {
            List<ProductCategoryTree> childCategories = productCategories.stream()
                    .filter(productCategory -> Objects.equals(productCategory.parentCategory(), parentCategory.getCategoryPath()))
                    .map(ProductCategoryTree::create)
                    .toList();

            parentCategory.addAllChildCategories(childCategories);

            generateCategoryTreeRecursively(productCategories, childCategories);
        }
    }
}
