package com.stylelab.category.repository.top;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.category.repository.ProductCategoryQueryDslRepository;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import com.stylelab.category.repository.dto.QProductCategoryCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.stylelab.category.domain.top.QHoodieAndSweatshirtProductCategory.hoodieAndSweatshirtProductCategory;

@Repository
@RequiredArgsConstructor
public class HoodieAndSweatshirtProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                hoodieAndSweatshirtProductCategory.id,
                                hoodieAndSweatshirtProductCategory.productId,
                                hoodieAndSweatshirtProductCategory.storeId,
                                hoodieAndSweatshirtProductCategory.storeName,
                                hoodieAndSweatshirtProductCategory.productCategoryPath,
                                hoodieAndSweatshirtProductCategory.productCategoryName,
                                hoodieAndSweatshirtProductCategory.productMainImage,
                                hoodieAndSweatshirtProductCategory.productMainImageType,
                                hoodieAndSweatshirtProductCategory.name,
                                hoodieAndSweatshirtProductCategory.price,
                                hoodieAndSweatshirtProductCategory.discountPrice,
                                hoodieAndSweatshirtProductCategory.discountRate,
                                hoodieAndSweatshirtProductCategory.soldOut,
                                hoodieAndSweatshirtProductCategory.deleted,
                                hoodieAndSweatshirtProductCategory.createdAt,
                                hoodieAndSweatshirtProductCategory.updatedAt
                        )
                )
                .from(hoodieAndSweatshirtProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(hoodieAndSweatshirtProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return hoodieAndSweatshirtProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return hoodieAndSweatshirtProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = hoodieAndSweatshirtProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = hoodieAndSweatshirtProductCategory.price.goe(price1).and(hoodieAndSweatshirtProductCategory.price.loe(price2));
        } else {
            goePrice = hoodieAndSweatshirtProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
