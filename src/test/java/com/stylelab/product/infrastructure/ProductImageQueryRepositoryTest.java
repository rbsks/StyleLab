package com.stylelab.product.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.product.infrastructure.dto.QProductDetailImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.stylelab.product.infrastructure.QProductImageEntity.productImageEntity;

@Slf4j
@SpringBootTest
public class ProductImageQueryRepositoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("상품 상세 이미지 목록 조회")
    public void findAllByProductId() {

        final Long productId = 60L;
        jpaQueryFactory
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
