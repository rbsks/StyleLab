package com.stylelab.category.presentation;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.exception.ProductCategoryError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String SUCCESS_CODE = "20000";
    private static final String SUCCESS_MESSAGE = "success";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .defaultRequest(
                        get("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                )
                .build();
    }

    @Nested
    @DisplayName("카테고리 트리 조회 테스트")
    public class FindAllCategories {

        @Test
        @DisplayName("카테고리 트리 조회 성공")
        public void successFindAllCategories() throws Exception {
            //given

            //when
            ResultActions resultActions =
                    mockMvc
                            .perform(get("/v1/categories"));

            //then
            resultActions
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(SUCCESS_CODE))
                    .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));
        }
    }


    @Nested
    @DisplayName("카테고리별 상품 목록 조회 테스트")
    public class FindByProductByConditions {

        @Test
        @DisplayName("카테고리별 상품 목록 조회 실패 - 유효하지 않은 카테고리 경로")
        public void failFindByProductByConditions() throws Exception {
            //given
            final String productCategoryPath = "00411211121";

            //when
            ResultActions resultActions =
                    mockMvc.
                            perform(
                                    get("/v1/categories/{productCategoryPath}", productCategoryPath)
                                            .param("page", "0")
                                            .param("size", "20")
                            );

            //then
            resultActions
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH.getCode()))
                    .andExpect(jsonPath("$.message").value(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH.getMessage()));
        }

        @Test
        @DisplayName("카테고리별 상품 목록 조회 성공")
        public void successFindByProductByConditions() throws Exception {
            //given
            final String productCategoryPath = ProductCategoryType.OUTER.getProductCategoryPath();

            //when
            ResultActions resultActions =
                    mockMvc.
                            perform(
                                    get("/v1/categories/{productCategoryPath}", productCategoryPath)
                                            .param("page", "0")
                                            .param("size", "20")
                            );

            //then
            resultActions
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(SUCCESS_CODE))
                    .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE))
                    .andExpect(jsonPath("$.nextToken").isNotEmpty())
                    .andExpect(jsonPath("$.lastPage").isNotEmpty())
                    .andExpect(jsonPath("$.items").isNotEmpty());
        }
    }
}
