package com.stylelab.product.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductOption2Test {

    @Test
    public void initProductOption1Id() throws Exception {
        //given
        ProductOption2 productOption2 = ProductOption2.builder()
                .productOption2Id(1L)
                .option2Name("M")
                .quantity(100)
                .build();

        //when
        Long productOption1Id = 2L;
        ProductOption2 initOption1IdProductOption2 = productOption2.initProductOption1Id(productOption1Id);

        //then
        assertThat(initOption1IdProductOption2).isNotEqualTo(productOption2);
        assertThat(initOption1IdProductOption2.productOption1Id()).isEqualTo(productOption1Id);
    }
}
