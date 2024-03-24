package com.stylelab.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylelab.common.annotation.WithAccount;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.user.exception.UserError;
import com.stylelab.user.presentation.request.CreateUserDeliveryAddressRequest;
import com.stylelab.user.presentation.request.SignInRequest;
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

import static com.stylelab.user.exception.UserError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT;
import static com.stylelab.user.exception.UserError.EMAIL_IS_REQUIRED;
import static com.stylelab.user.exception.UserError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT;
import static com.stylelab.user.exception.UserError.PASSWORD_IS_REQUIRED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

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
    @DisplayName("배송지 등록 테스트")
    public class CreateUserDeliveryAddressTest {

        @Test
        @Rollback
        @Transactional
        @DisplayName("배송지 요청 객체의 검증이 통과하면 배송지 등록에 성공한다.")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void successCreateUserDeliveryAddressTest() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .address("경기도 성남시 분당구 정자일로 95 네이버")
                    .addressDetail("문앞")
                    .postalCode("13561")
                    .addressAliases("네이버")
                    .defaultDeliveryAddress(false)
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"));
        }

        @Test
        @DisplayName("배송지 등록 실패 - 배송지 주소가 null 인 경우")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void failureCreateUserDeliveryAddress_01() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .addressDetail("문앞")
                    .postalCode("13561")
                    .addressAliases("네이버")
                    .defaultDeliveryAddress(false)
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(UserError.DELIVERY_ADDRESS_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(UserError.DELIVERY_ADDRESS_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("배송지 등록 실패 - 배송지 상세 주소가 null 인 경우")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void failureCreateUserDeliveryAddress_02() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .address("경기도 성남시 분당구 정자일로 95 네이버")
                    .postalCode("13561")
                    .addressAliases("네이버")
                    .defaultDeliveryAddress(false)
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(UserError.DELIVERY_ADDRESS_DETAIL_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(UserError.DELIVERY_ADDRESS_DETAIL_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("배송지 등록 실패 - 우편 번호가 null 인 경우")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void failureCreateUserDeliveryAddress_03() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .address("경기도 성남시 분당구 정자일로 95 네이버")
                    .addressDetail("문앞")
                    .addressAliases("네이버")
                    .defaultDeliveryAddress(false)
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(UserError.DELIVERY_POSTAL_CODE_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(UserError.DELIVERY_POSTAL_CODE_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("배송지 등록 실패 - 배송지 주소 별칭이 null 인 경우")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void failureCreateUserDeliveryAddress_04() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .address("경기도 성남시 분당구 정자일로 95 네이버")
                    .addressDetail("문앞")
                    .postalCode("13561")
                    .defaultDeliveryAddress(false)
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(UserError.DELIVERY_ADDRESS_ALIASES_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(UserError.DELIVERY_ADDRESS_ALIASES_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("배송지 등록 실패 - 배송지 기본 주소지 여부가 null 인 경우")
        @WithAccount(email = "test@gmail.com", role = "ROLE_USER", type = UserType.USER)
        public void failureCreateUserDeliveryAddress_05() throws Exception {
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                    .address("경기도 성남시 분당구 정자일로 95 네이버")
                    .addressDetail("문앞")
                    .postalCode("13561")
                    .addressAliases("네이버")
                    .build();

            mockMvc.perform(post("/v1/users/deliveries")
                            .content(objectMapper.writeValueAsString(createUserDeliveryAddressRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(UserError.DELIVERY_DEFAULT_ADDRESS_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(UserError.DELIVERY_DEFAULT_ADDRESS_REQUIRE.getMessage()));
        }
    }
}