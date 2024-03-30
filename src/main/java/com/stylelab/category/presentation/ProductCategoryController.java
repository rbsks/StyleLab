package com.stylelab.category.presentation;

import com.stylelab.category.application.ProductCategoryCollection;
import com.stylelab.category.application.ProductCategoryQueryCriteria;
import com.stylelab.category.application.ProductCategoryService;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import com.stylelab.category.presentation.response.ProductCategoryCollectionResponse;
import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.dto.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProductCategoriesResponse>> findAllCategories() {

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        ProductCategoriesResponse.create(productCategoryService.findAllCategories())
                )
        );
    }

    @GetMapping("/{productCategoryPath}")
    public ResponseEntity<ApiResponse<PagingResponse<ProductCategoryCollectionResponse>>> findByProductByConditions(
            @PathVariable(name = "productCategoryPath", required = false) final String productCategoryPath,
            @RequestParam(name = "nextToken", required = false) final Long productId,
            @RequestParam(name = "productName", required = false) final String productName,
            @RequestParam(name = "price1", required = false) final Integer price1,
            @RequestParam(name = "price2", required = false) final Integer price2,
            @RequestParam(name = "discountRate", required = false) final Integer discountRate,
            Pageable pageable) {

        ProductCategoryQueryCriteria productCategoryQueryCriteria = ProductCategoryQueryCriteria.create(
                productId, productName, productCategoryPath, price1, price2, discountRate, pageable
        );

        ProductCategoryCollection productCategoryConditions =
                productCategoryService.findAllProductCategoryConditions(productCategoryQueryCriteria);

        return ResponseEntity.ok(
                ApiResponse.createApiResponse(
                        PagingResponse.createCursorPagingResponse(
                                productCategoryConditions.productCategoryDtos()
                                        .map(ProductCategoryCollectionResponse::create),
                                productCategoryConditions.nextToken()
                        )
                )
        );
    }
}
