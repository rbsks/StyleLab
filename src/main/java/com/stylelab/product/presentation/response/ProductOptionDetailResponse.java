package com.stylelab.product.presentation.response;

import java.util.List;

public record ProductOptionDetailResponse(
        List<Option> options
) {

    public record Option(
            Option1 option1,
            List<Option2> option2s
    ) {

    }

    public record Option1(
            Long productOption1Id,
            Long productId,
            String option1Name,
            int quantity,
            int additionalPrice,
            boolean soldOut,
            boolean deleted
    ) {

    }

    public record Option2(
            Long productOption2Id,
            Long productId,
            String option2Name,
            int quantity,
            int additionalPrice,
            boolean soldOut,
            boolean deleted
    ) {

    }
}
