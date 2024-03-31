package com.stylelab.storeproduct.application;

import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.storeproduct.presentation.request.CreateStoreProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CreateStoreProductCommandTest {

    @Test
    @DisplayName("storeId와 requestStoreId가 같지 않으면 CreateStoreProductCommand 생성에 실패한다.")
    public void test() throws Exception {
        //given
        Long storeId = 1L;
        Long requestStoreId = 2L;
        CreateStoreProductRequest createStoreProductRequest = nonUseOptionCreateStoreProductRequest(storeId);

        //when
        Throwable throwable = catchThrowable(() ->
                CreateStoreProductCommand.create(storeId, requestStoreId, createStoreProductRequest));

        //then
        assertThat(throwable).isInstanceOf(StoreException.class);
        assertThat(throwable.getMessage()).isEqualTo(StoreError.FORBIDDEN_STORE.getMessage());
    }

    private CreateStoreProductRequest nonUseOptionCreateStoreProductRequest(Long storeId) {

        return CreateStoreProductRequest.builder()
                .entryMain(
                        CreateStoreProductRequest.EntryMain.builder()
                                .entryMain("https://image1")
                                .build()
                )
                .entrySubs(
                        Arrays.asList(
                                CreateStoreProductRequest.EntrySub.builder().entrySub("https://image1").build(),
                                CreateStoreProductRequest.EntrySub.builder().entrySub("https://image2").build(),
                                CreateStoreProductRequest.EntrySub.builder().entrySub("https://image3").build(),
                                CreateStoreProductRequest.EntrySub.builder().entrySub("https://image4").build(),
                                CreateStoreProductRequest.EntrySub.builder().entrySub("https://image5").build()
                        )
                )
                .descriptions(
                        Arrays.asList(
                                CreateStoreProductRequest.Description.builder().description("https://image1").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image2").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image3").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image4").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image5").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image6").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image7").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image8").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image9").build(),
                                CreateStoreProductRequest.Description.builder().description("https://image10").build()
                        )
                )
                .productRequest(
                        CreateStoreProductRequest.ProductRequest.builder()
                                .storeId(storeId)
                                .productCategoryPath("001001001")
                                .name("coby 맨투맨")
                                .price(35000)
                                .discountRate(100)
                                .useOption(false)
                                .quantity(10000)
                                .optionDepth(0)
                                .build()
                ).build();
    }
}
