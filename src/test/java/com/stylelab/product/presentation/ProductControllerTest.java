package com.stylelab.product.presentation;

import com.stylelab.product.exception.ProductError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("상품 상세 조회 테스트")
    public class findByProductIdTest {

        @Test
        @DisplayName("상품 상세 조회 성공")
        public void successFindByProductByConditionsTest() throws Exception {
            //given
            final Long productId = 2_456_123L;

            //when
            mockMvc.perform(get("/v1/products/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.product.productId").value(productId));
        }

        @Test
        @DisplayName("상품 상세 조회 실패 - 존재하지 않는 상품인 경우")
        public void failureFindByProductByConditionsTest() throws Exception {
            //given
            final Long productId = 100_000_000_000L;

            //when
            mockMvc.perform(get("/v1/products/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ProductError.NOT_FOUND_PRODUCT.getCode()))
                    .andExpect(jsonPath("$.message").value(ProductError.NOT_FOUND_PRODUCT.getMessage()));
        }
    }
}
