package com.stylelab.storeproduct.application;

import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.domain.ProductOption2;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.infrastructure.ProductOption1Repository;
import com.stylelab.product.infrastructure.ProductOption2Repository;
import com.stylelab.product.infrastructure.ProductRepository;
import com.stylelab.product.infrastructure.dto.ProductDetail;
import com.stylelab.storeproduct.presentation.request.CreateStoreProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRegistrationServiceTest {

    private final Long storeId = 1L;
    private final Long requestStoreId = 1L;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOption1Repository productOption1Repository;
    @Autowired
    private ProductOption2Repository productOption2Repository;
    @Autowired
    private ProductRegistrationService productRegistrationService;

    @Test
    @Transactional
    @DisplayName("스토어 상품 등록 성공 - useOption false인 경우 ")
    public void successCreateStoreProduct_01() throws Exception {
        //given
        CreateStoreProductRequest createStoreProductRequest = nonUseOptionCreateStoreProductRequest(storeId);
        CreateStoreProductCommand createStoreProductCommand =
                CreateStoreProductCommand.create(storeId, requestStoreId, createStoreProductRequest);

        //when
        Long storeProductId = productRegistrationService.createStoreProduct(createStoreProductCommand);

        //then
        ProductDetail productDetail = productRepository.findByProductId(storeProductId)
                .orElseThrow(() -> new ProductException(ProductError.NOT_FOUND_PRODUCT));
        assertThat(productDetail.productId()).isEqualTo(storeProductId);
    }

    @Test
    @Transactional
    @DisplayName("스토어 상품 등록 성공 - useOption ture, optionDepth가 1인 경우")
    public void successCreateStoreProduct_02() throws Exception {
        //given
        CreateStoreProductRequest createStoreProductRequest = useOptionOptionDepth1CreateStoreProductRequest(storeId);
        CreateStoreProductCommand createStoreProductCommand =
                CreateStoreProductCommand.create(storeId, requestStoreId, createStoreProductRequest);

        //when
        Long storeProductId = productRegistrationService.createStoreProduct(createStoreProductCommand);

        //then
        ProductDetail productDetail = productRepository.findByProductId(storeProductId)
                .orElseThrow(() -> new ProductException(ProductError.NOT_FOUND_PRODUCT));
        assertThat(productDetail.productId()).isEqualTo(storeProductId);

        List<ProductOption1> productOption1s = productOption1Repository.findAllByProductId(storeProductId);
        for (ProductOption1 productOption1 : productOption1s) {
            assertThat(productOption1.productId()).isEqualTo(storeProductId);
        }
    }

    @Test
    @Transactional
    @DisplayName("스토어 상품 등록 성공 - useOption ture, optionDepth가 2인 경우")
    public void successCreateStoreProduct_03() throws Exception {
        //given
        Long storeId = 1L;
        CreateStoreProductRequest createStoreProductRequest = useOptionOptionDepth2CreateStoreProductRequest(storeId);
        CreateStoreProductCommand createStoreProductCommand =
                CreateStoreProductCommand.create(storeId, requestStoreId, createStoreProductRequest);

        //when
        Long storeProductId = productRegistrationService.createStoreProduct(createStoreProductCommand);

        //then
        ProductDetail productDetail = productRepository.findByProductId(storeProductId)
                .orElseThrow(() -> new ProductException(ProductError.NOT_FOUND_PRODUCT));
        assertThat(productDetail.productId()).isEqualTo(storeProductId);

        List<ProductOption1> productOption1s = productOption1Repository.findAllByProductId(storeProductId);
        for (ProductOption1 productOption1 : productOption1s) {
            assertThat(productOption1.productId()).isEqualTo(storeProductId);

            Long productOption1Id = productOption1.productOption1Id();
            List<ProductOption2> productOption2s =
                    productOption2Repository.findAllByProductOption1Id(productOption1Id);
            for (ProductOption2 productOption2 : productOption2s) {
                assertThat(productOption2.productOption1Id()).isEqualTo(productOption1Id);
            }
        }
    }

    private CreateStoreProductRequest useOptionOptionDepth2CreateStoreProductRequest(Long storeId) {
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
                                .useOption(true)
                                .quantity(0)
                                .optionDepth(2)
                                .option1("색상")
                                .option2("사이즈")
                                .build()
                )
                .productOption1sRequests(
                        Arrays.asList(
                                CreateStoreProductRequest.ProductOption1sRequest.builder()
                                        .option1Name("화이트")
                                        .additionalPrice(0)
                                        .quantity(0)
                                        .productOption2sRequests(
                                                Arrays.asList(
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("M")
                                                                .additionalPrice(0)
                                                                .quantity(10000)
                                                                .build(),
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("L")
                                                                .additionalPrice(5000)
                                                                .quantity(10000)
                                                                .build(),
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("XL")
                                                                .additionalPrice(10000)
                                                                .quantity(10000)
                                                                .build()
                                                )
                                        )
                                        .build(),
                                CreateStoreProductRequest.ProductOption1sRequest.builder()
                                        .option1Name("블랙")
                                        .additionalPrice(0)
                                        .quantity(0)
                                        .productOption2sRequests(
                                                Arrays.asList(
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("M")
                                                                .additionalPrice(0)
                                                                .quantity(10000)
                                                                .build(),
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("L")
                                                                .additionalPrice(5000)
                                                                .quantity(10000)
                                                                .build(),
                                                        CreateStoreProductRequest.ProductOption2sRequest.builder()
                                                                .option2Name("XL")
                                                                .additionalPrice(10000)
                                                                .quantity(10000)
                                                                .build()
                                                )
                                        )
                                        .build()
                        )
                )
                .build();
    }

    private CreateStoreProductRequest useOptionOptionDepth1CreateStoreProductRequest(Long storeId) {
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
                                .useOption(true)
                                .quantity(0)
                                .optionDepth(1)
                                .option1("사이즈")
                                .build()
                )
                .productOption1sRequests(
                        Arrays.asList(
                                CreateStoreProductRequest.ProductOption1sRequest.builder()
                                        .option1Name("M")
                                        .additionalPrice(0)
                                        .quantity(10000)
                                        .build(),
                                CreateStoreProductRequest.ProductOption1sRequest.builder()
                                        .option1Name("L")
                                        .additionalPrice(5000)
                                        .quantity(10000)
                                        .build(),
                                CreateStoreProductRequest.ProductOption1sRequest.builder()
                                        .option1Name("XL")
                                        .additionalPrice(10000)
                                        .quantity(10000)
                                        .build()
                        )
                )
                .build();
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
