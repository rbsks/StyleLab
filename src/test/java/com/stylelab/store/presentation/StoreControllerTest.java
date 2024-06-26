package com.stylelab.store.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.stylelab.store.exception.StoreError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT;
import static com.stylelab.store.exception.StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT;
import static com.stylelab.store.exception.StoreError.STORE_STAFF_EMAIL_REQUIRE;
import static com.stylelab.store.exception.StoreError.STORE_STAFF_PASSWORD_REQUIRE;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {

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
    @DisplayName("스토어, 스토어 스태프 등록 테스트")
    public class ApplyStoreTest {

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 brand 값이 null인 경우 StoreException(BRAND_NAME_REQUIRE)이 발생한다.")
        public void failApplyStore_01() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BRAND_NAME_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BRAND_NAME_REQUIRE.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 brand 값이 유효하지 않은 경우 StoreException(BRAND_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_02() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby!@#!")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BRAND_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BRAND_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 name이 null인 경우 StoreException(BUSINESS_NAME_REQUIRE)이 발생한다.")
        public void failApplyStore_03() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_NAME_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_NAME_REQUIRE.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 name값이 유효하지 않은 경우 StoreException(BUSINESS_NAME_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_04() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby!@!$!")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 스토어 주소가 null인 경우 StoreException(BUSINESS_ADDRESS_REQUIRE)이 발생한다.")
        public void failApplyStore_05() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_ADDRESS_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_ADDRESS_REQUIRE.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 스토어 상세 주소가 null인 경우 StoreException(BUSINESS_ADDRESS_DETAIL_REQUIRE)이 발생한다.")
        public void failApplyStore_06() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_ADDRESS_DETAIL_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_ADDRESS_DETAIL_REQUIRE.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 스토어 우편번호가 null인 경우 StoreException(BUSINESS_POSTAL_CODE_REQUIRE)이 발생한다.")
        public void failApplyStore_07() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_POSTAL_CODE_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_POSTAL_CODE_REQUIRE.getMessage()));

        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 요청 객체에 스토어 사업자 등록번호가 null인 경우 StoreException(BUSINESS_LICENSE_NUMBER_REQUIRE)이 발생한다.")
        public void failApplyStore_08() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.BUSINESS_LICENSE_NUMBER_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.BUSINESS_LICENSE_NUMBER_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 이메일이 null인 경우 StoreException(STORE_STAFF_EMAIL_REQUIRE)이 발생한다.")
        public void failApplyStore_09() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(STORE_STAFF_EMAIL_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(STORE_STAFF_EMAIL_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 이메일값이 유효하지 않은 경우 StoreException(EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_10() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver..com")
                    .name("한규빈")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 이름 null인 경우 StoreException(STORE_STAFF_NAME_REQUIRE)이 발생한다.")
        public void failApplyStore_11() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.STORE_STAFF_NAME_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.STORE_STAFF_NAME_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 이름값이 유효하지 않은 경우 StoreException(NAME_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_12() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@never.com")
                    .name("한규빈!@!#")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 password가 null인 경우 StoreException(STORE_STAFF_PASSWORD_REQUIRE)이 발생한다.")
        public void failApplyStore_13() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver.com")
                    .name("한규빈")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(STORE_STAFF_PASSWORD_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(STORE_STAFF_PASSWORD_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 password 값이 유효하지 않은 경우 StoreException(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_14() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@never.com")
                    .name("한규빈")
                    .password("test1234")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 confirmPassword가 null인 경우 StoreException(STORE_STAFF_CONFIRM_PASSWORD_REQUIRE)이 발생한다.")
        public void failApplyStore_15() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver.com")
                    .name("한규빈")
                    .password("test12341!@!")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.STORE_STAFF_CONFIRM_PASSWORD_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.STORE_STAFF_CONFIRM_PASSWORD_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 confirmPassword 값이 유효하지 않은 경우 StoreException(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_16() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@never.com")
                    .name("한규빈")
                    .password("test1234!@!#!")
                    .confirmPassword("test1234123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 nickname이 null인 경우 StoreException(STORE_STAFF_NICKNAME_REQUIRE)이 발생한다.")
        public void failApplyStore_17() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver.com")
                    .name("한규빈")
                    .password("test12341!@!")
                    .confirmPassword("test12341!@!")
                    .phoneNumber("01012341234")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.STORE_STAFF_NICKNAME_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.STORE_STAFF_NICKNAME_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 nickname 값이 유효하지 않은 경우 StoreException(NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_18() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@never.com")
                    .name("한규빈")
                    .password("test1234!@!#!")
                    .confirmPassword("test1234!@!#!")
                    .phoneNumber("01012341234")
                    .nickname("coby!!@")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 phoneNumber가 null인 경우 StoreException(STORE_STAFF_PHONE_NUMBER_REQUIRE)이 발생한다.")
        public void failApplyStore_19() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@naver.com")
                    .name("한규빈")
                    .password("test12341!@!")
                    .confirmPassword("test12341!@!")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.STORE_STAFF_PHONE_NUMBER_REQUIRE.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.STORE_STAFF_PHONE_NUMBER_REQUIRE.getMessage()));
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프 요청 객체에 phoneNumber 값이 유효하지 않은 경우 StoreException(PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void failApplyStore_20() throws Exception {
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

            ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .email("test@never.com")
                    .name("한규빈")
                    .password("test1234!@!#!")
                    .confirmPassword("test1234!@!#!")
                    .phoneNumber("0101234123421323")
                    .nickname("coby")
                    .build();
            ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            mockMvc.perform(post("/v1/stores/apply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(applyStoreRequest)))
                    .andDo(print())
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(StoreError.PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(StoreError.PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }
    }

}
