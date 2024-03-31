package com.stylelab.storeproduct.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylelab.common.annotation.WithAccount;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.storeproduct.presentation.request.CreateStoreProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StoreProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Nested
    @DisplayName("스토어 상품 등록 테스트")
    public class CreateStoreProductTest {

        @Test
        @Rollback
        @Transactional
        @DisplayName("스토어 상품 등록 성공 - useOption false인 경우 ")
        @WithAccount(email = "tester@naver.com", role = "ROLE_STORE_OWNER", type = UserType.STORE)
        public void successCreateStoreProduct_01() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequest createStoreProductRequest = CreateStoreProductRequest.builder()
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

            //when
            mockMvc.perform(post("/v1/stores/{storeId}/products", storeId)
                    .content(objectMapper.writeValueAsString(createStoreProductRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"));
        }

        @Test
        @Rollback
        @Transactional
        @DisplayName("스토어 상품 등록 성공 - useOption ture, optionDepth가 1인 경우")
        @WithAccount(email = "tester@naver.com", role = "ROLE_STORE_OWNER", type = UserType.STORE)
        public void successCreateStoreProduct_02() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequest createStoreProductRequest = CreateStoreProductRequest.builder()
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

            //when
            mockMvc.perform(post("/v1/stores/{storeId}/products", storeId)
                            .content(objectMapper.writeValueAsString(createStoreProductRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"));
        }

        @Test
        @Rollback
        @Transactional
        @DisplayName("스토어 상품 등록 성공 - useOption ture, optionDepth가 2인 경우")
        @WithAccount(email = "tester@naver.com", role = "ROLE_STORE_OWNER", type = UserType.STORE)
        public void successCreateStoreProduct_03() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequest createStoreProductRequest = CreateStoreProductRequest.builder()
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

            //when
            mockMvc.perform(post("/v1/stores/{storeId}/products", storeId)
                            .content(objectMapper.writeValueAsString(createStoreProductRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"));
        }
    }

}
