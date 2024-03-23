package com.stylelab.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.file.constant.ImageType;
import com.stylelab.product.repository.dto.ProductCollection;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.repository.dto.ProductDetailImage;
import com.stylelab.product.repository.dto.QProductCollection;
import com.stylelab.product.repository.dto.QProductDetail;
import com.stylelab.product.repository.dto.QProductDetailImage;
import com.stylelab.store.constant.ApproveType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.stylelab.category.infrastructure.QProductCategoryEntity.productCategoryEntity;
import static com.stylelab.product.domain.QProduct.product;
import static com.stylelab.product.domain.QProductImage.productImage;
import static com.stylelab.store.domain.QStore.store;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {

        List<ProductCollection> items = jpaQueryFactory
                .select(
                        new QProductCollection(
                                product.productId,
                                product.name,
                                productCategoryEntity.categoryPath,
                                productCategoryEntity.categoryName,
                                product.price,
                                product.discountPrice,
                                product.discountRate,
                                productImage.imageUrl,
                                productImage.imageType
                        )
                )
                .from(product)
                .innerJoin(productImage).on(
                        product.productId.eq(productImage.product.productId)
                                .and(productImage.imageType.eq(ImageType.PRODUCT_ENTRY_MAIN))
                )
                .innerJoin(productCategoryEntity).on(product.productCategoryPath.eq(productCategoryEntity.categoryPath))
                .where(
                        likeProductCategoryPath(productCategoryPath),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .where(
                        likeProductCategoryPath(productCategoryPath),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                );

        return PageableExecutionUtils.getPage(
                items,
                pageable,
                count::fetchOne
        );
    }

    @Override
    public Optional<ProductDetail> findByProductId(final Long productId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(
                                new QProductDetail(
                                        product.productId,
                                        store.storeId,
                                        store.name,
                                        store.brand,
                                        productCategoryEntity.categoryPath,
                                        productCategoryEntity.categoryName,
                                        product.name,
                                        product.price,
                                        product.discountPrice,
                                        product.discountRate,
                                        product.useOption,
                                        product.optionDepth,
                                        product.option2,
                                        product.option1,
                                        product.quantity,
                                        product.soldOut,
                                        product.deleted
                                )
                        )
                        .from(product)
                        .innerJoin(store).on(product.store.storeId.eq(store.storeId))
                        .innerJoin(productCategoryEntity).on(product.productCategoryPath.eq(productCategoryEntity.categoryPath))
                        .where(
                                product.productId.eq(productId),
                                product.deleted.eq(false),
                                store.approveType.eq(ApproveType.APPROVE)
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<ProductDetailImage> findAllByProductId(Long productId) {
        return jpaQueryFactory
                .select(
                        new QProductDetailImage(
                                productImage.productImageId,
                                productImage.product.productId,
                                productImage.imageUrl,
                                productImage.imageOrder,
                                productImage.imageType
                        )
                )
                .from(productImage)
                .where(productImage.product.productId.eq(productId))
                .fetch();
    }

    private BooleanExpression likeProductCategoryPath(String productCategoryPath) {
        if (!StringUtils.hasText(productCategoryPath)) {
            return null;
        }

        return product.productCategoryPath.like(productCategoryPath + "%");
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return product.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = product.price.goe(price1);
        } else if (price1 != null) {
            goePrice = product.price.goe(price1).and(product.price.loe(price2));
        } else {
            goePrice = product.price.loe(price2);
        }

        return goePrice;
    }
}
