package com.stylelab.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.product.repository.dto.ProductDetailImage;
import com.stylelab.product.repository.dto.QProductDetailImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.stylelab.product.domain.QProductImage.productImage;

@Repository
@RequiredArgsConstructor
public class ProductImageQueryRepositoryImpl implements ProductImageQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductDetailImage> findAllByProductId(final Long productId) {
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
}
