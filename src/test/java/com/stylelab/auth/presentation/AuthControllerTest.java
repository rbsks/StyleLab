package com.stylelab.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylelab.auth.exception.AuthError;
import com.stylelab.auth.presentation.request.SignInRequest;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

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
    @DisplayName("유저 로그인 테스트")
    public class UserSignInTest {

        @Test
        @DisplayName("로그인 실패 - 유효하지 않은 이메일인 경우 실패")
        public void failureSignIn_01() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("tester@@gmail..com")
                    .password("tester!@31241")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 이메일이 null 인 경우 실패")
        public void failureSignIn_02() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .password("tester!@31241")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.EMAIL_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.EMAIL_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 유효하지 않은 비밀번호인 경우 실패")
        public void failureSignIn_03() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("test@gmail.com")
                    .password("tester")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 비밀번호가 null 인 경우 실패")
        public void failureSignIn_04() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("test@gmail.com")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.PASSWORD_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.PASSWORD_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 존재하는 회원이지만 비밀번호가 유효하지 않은 경우 실패")
        public void failureSignIn_05() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("test@gmail.com")
                    .password("test1124!@!#!")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.INVALID_PASSWORD.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.INVALID_PASSWORD.getMessage()));
        }

        @Test
        @DisplayName("로그인 성공")
        public void successSignIn() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("test@gmail.com")
                    .password("test1234!@!")
                    .build();

            mockMvc.perform(post("/v1/auth/users/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.token").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("스토어 로그인 테스트")
    public class StoreSignInTest {

        @Test
        @DisplayName("로그인 실패 - 유효하지 않은 이메일인 경우 실패")
        public void failureSignIn_01() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("tester@@gmail..com")
                    .password("tester!@31241")
                    .build();

            mockMvc.perform(post("/v1/auth/stores/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 이메일이 null 인 경우 실패")
        public void failureSignIn_02() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .password("tester!@31241")
                    .build();

            mockMvc.perform(post("/v1/auth/stores/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.EMAIL_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.EMAIL_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 유효하지 않은 비밀번호인 경우 실패")
        public void failureSignIn_03() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("tester@gmail.com")
                    .password("tester")
                    .build();

            mockMvc.perform(post("/v1/auth/stores/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("로그인 실패 - 비밀번호가 null 인 경우 실패")
        public void failureSignIn_04() throws Exception {
            SignInRequest signInRequest = SignInRequest.builder()
                    .email("tester@gmail.com")
                    .build();

            mockMvc.perform(post("/v1/auth/stores/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(AuthError.PASSWORD_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(AuthError.PASSWORD_IS_REQUIRED.getMessage()));
        }
    }
}
