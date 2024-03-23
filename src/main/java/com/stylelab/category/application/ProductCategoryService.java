package com.stylelab.category.application;

import com.stylelab.category.domain.ProductCategory;
import com.stylelab.category.infrastructure.ProductCategoryRepository;
import com.stylelab.category.infrastructure.dto.ProductCategoryCondition;
import com.stylelab.category.infrastructure.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Cacheable("productCategoryTree")
    public List<ProductCategoryTree> findAllCategories() {

        List<ProductCategory> productCategories = productCategoryRepository.findAllCategories();

        List<ProductCategoryTree> parentCategories = productCategories.stream()
                .filter(productCategory -> !StringUtils.hasText(productCategory.parentCategory()))
                .map(ProductCategoryTree::create)
                .toList();

        generateCategoryTreeRecursively(productCategories, parentCategories);

        return parentCategories;
    }

    public ProductCategoryCollection findAllProductCategoryConditions(ProductCategoryQueryCriteria productCategoryQueryCriteria) {

        Slice<ProductCategoryDto> products = productCategoryRepository.findAllProductCategoryConditions(
                ProductCategoryCondition.create(
                        productCategoryQueryCriteria.productCategoryType(), productCategoryQueryCriteria.productId(),
                        productCategoryQueryCriteria.productName(), productCategoryQueryCriteria.productCategoryPath(),
                        productCategoryQueryCriteria.price1(), productCategoryQueryCriteria.price2(),
                        productCategoryQueryCriteria.discountRate(), productCategoryQueryCriteria.pageable()
                )
        );

        List<ProductCategoryDto> content = products.getContent();

        return ProductCategoryCollection.create(products, !products.isLast() ? content.get(products.getSize() - 1).productId() : null);
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
