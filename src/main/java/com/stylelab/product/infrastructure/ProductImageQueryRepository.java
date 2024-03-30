package com.stylelab.product.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.product.infrastructure.dto.ProductDetailImage;
import com.stylelab.product.infrastructure.dto.QProductDetailImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.stylelab.product.infrastructure.QProductImageEntity.productImageEntity;


@Repository
@RequiredArgsConstructor
public class ProductImageQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductDetailImage> findAllByProductId(final Long productId) {


        return jpaQueryFactory
                .select(
                        new QProductDetailImage(
                                productImageEntity.productImageId,
                                productImageEntity.productId,
                                productImageEntity.imageUrl,
                                productImageEntity.imageOrder,
                                productImageEntity.imageType
                        )
                )
                .from(productImageEntity)
                .where(productImageEntity.productId.eq(productId))
                .fetch();
    }
}
