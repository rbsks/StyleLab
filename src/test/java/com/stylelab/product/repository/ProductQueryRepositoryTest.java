package com.stylelab.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.product.repository.dto.ProductCollection;
import com.stylelab.product.repository.dto.ProductDetail;
import com.stylelab.product.repository.dto.QProductCollection;
import com.stylelab.product.repository.dto.QProductDetail;
import com.stylelab.product.repository.dto.QProductDetailImage;
import com.stylelab.storage.constant.ImageType;
import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.infrastructure.QStoreEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.stylelab.category.infrastructure.QProductCategoryEntity.productCategoryEntity;
import static com.stylelab.product.domain.QProduct.product;
import static com.stylelab.product.domain.QProductImage.productImage;
import static com.stylelab.store.infrastructure.QStoreEntity.storeEntity;

@Slf4j
@SpringBootTest
public class ProductQueryRepositoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 목록 조회 query test - where 조건이 아무것도 없는 경우")
    public void findByProductByConditions_01() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //then
    }

    @Test
    @DisplayName("상품 목록 조회 query test - where productCategoryPath")
    public void findByProductByConditions_02() throws Exception {
        //given
        String productCategoryPath = "001001001";
        Pageable pageable = PageRequest.of(0, 10);

        //when
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
                        likeProductCategoryPath(productCategoryPath)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //then
    }

    @Test
    @DisplayName("상품 목록 조회 query test - where productCategoryPath and discountRate")
    public void findByProductByConditions_03() throws Exception {
        //given
        String productCategoryPath = "001001001";
        Integer discountRate = 10;
        Pageable pageable = PageRequest.of(0, 10);

        //when
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
                        eqDiscountRate(discountRate)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //then
    }

    @Test
    @DisplayName("상품 목록 조회 query test - where productCategoryPath and discountRate and price1 and price2")
    public void findByProductByConditions_04() throws Exception {
        //given
        String productCategoryPath = "001001001";
        Integer discountRate = 10;
        Integer price1 = 35000;
        Integer price2 = null;
        Pageable pageable = PageRequest.of(0, 10);

        //when
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

        //then
    }

    @Test
    @DisplayName("상품 목록 조회 count query test")
    public void findByProductByConditionsCount() throws Exception {
        //given
        String productCategoryPath = "001001001";
        Integer discountRate = null;
        Integer price1 = 35000;
        Integer price2 = null;

        //when
        Long count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .where(
                        likeProductCategoryPath(productCategoryPath),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .fetchOne();

        //then
        log.info("findByProductByConditionsCount: {}", count);
    }

    @Test
    @DisplayName("상품 상세 조회")
    public void findByProductId() {
        final Long productId = 60L;

        ProductDetail productDetail = jpaQueryFactory
                .select(
                        new QProductDetail(
                                product.productId,
                                storeEntity.storeId,
                                storeEntity.name,
                                storeEntity.brand,
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
                .innerJoin(storeEntity).on(product.storeEntity.storeId.eq(storeEntity.storeId))
                .innerJoin(productCategoryEntity).on(product.productCategoryPath.eq(productCategoryEntity.categoryPath))
                .where(
                        product.productId.eq(productId),
                        product.deleted.eq(false),
                        storeEntity.approveType.eq(ApproveType.APPROVE)
                )
                .fetchOne();
    }

    @Test
    @DisplayName("상품 상세 이미지 목록 조회")
    public void findAllByProductId() {
        final Long productId = 60L;
        jpaQueryFactory
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
