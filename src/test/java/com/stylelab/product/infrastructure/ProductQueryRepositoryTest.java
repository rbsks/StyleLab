package com.stylelab.product.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.product.infrastructure.dto.QProductDetail;
import com.stylelab.store.constant.ApproveType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.stylelab.category.infrastructure.QProductCategoryEntity.productCategoryEntity;
import static com.stylelab.product.infrastructure.QProductEntity.productEntity;
import static com.stylelab.store.infrastructure.QStoreEntity.storeEntity;

@Slf4j
@SpringBootTest
public class ProductQueryRepositoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("상품 상세 조회")
    public void findByProductId() {
        final Long productId = 60L;

        ProductDetail productDetail = jpaQueryFactory
                .select(
                        new QProductDetail(
                                productEntity.productId,
                                storeEntity.storeId,
                                storeEntity.name,
                                storeEntity.brand,
                                productCategoryEntity.categoryPath,
                                productCategoryEntity.categoryName,
                                productEntity.name,
                                productEntity.price,
                                productEntity.discountPrice,
                                productEntity.discountRate,
                                productEntity.useOption,
                                productEntity.optionDepth,
                                productEntity.option2,
                                productEntity.option1,
                                productEntity.quantity,
                                productEntity.soldOut,
                                productEntity.deleted
                        )
                )
                .from(productEntity)
                .innerJoin(storeEntity).on(productEntity.storeId.eq(storeEntity.storeId))
                .innerJoin(productCategoryEntity).on(productEntity.productCategoryPath.eq(productCategoryEntity.categoryPath))
                .where(
                        productEntity.productId.eq(productId),
                        productEntity.deleted.eq(false),
                        storeEntity.approveType.eq(ApproveType.APPROVE)
                )
                .fetchOne();
    }
}
