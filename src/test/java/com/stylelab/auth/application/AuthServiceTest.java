package com.stylelab.auth.application;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.auth.exception.AuthException;
import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.infrastructure.StoreEntity;
import com.stylelab.store.infrastructure.StoreJpaRepository;
import com.stylelab.store.infrastructure.StoreStaffEntity;
import com.stylelab.store.infrastructure.StoreStaffJpaRepository;
import com.stylelab.user.constant.UserRole;
import com.stylelab.user.infrastructure.UserEntity;
import com.stylelab.user.infrastructure.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private StoreJpaRepository storeJpaRepository;
    @Autowired
    private StoreStaffJpaRepository storeStaffJpaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("유저 로그인 테스트")
    public class UserSignIn {

        UserEntity userEntity;
        String password = "testpassword";
        String encodePassword = passwordEncoder.encode(password);
        String uuid = UUID.randomUUID().toString().substring(0, 7);

        @BeforeEach
        public void init() {

            userEntity = userJpaRepository.save(
                    UserEntity.builder()
                            .name(uuid)
                            .email(uuid + "@gmail.com")
                            .password(encodePassword)
                            .nickname(uuid)
                            .phoneNumber("99988887777")
                            .role(UserRole.ROLE_USER)
                            .build()
            );
        }

        @Test
        @Transactional
        @DisplayName("로그인이 성공하여 토큰 발급에 성공한다.")
        public void successUserSignIn() throws Exception {
            //given
            String email = userEntity.getEmail();
            SignInUser signInUser = SignInUser.create(email, password);

            //when
            String token = authService.userSignIn(signInUser);

            //then
            assertThat(token).isNotBlank();
        }

        @Test
        @Transactional
        @DisplayName("올바르지 않은 비밀번호인 경우 로그인에 실패한다.")
        public void failUserSignIn() throws Exception {
            //given
            String email = userEntity.getEmail();
            SignInUser signInUser = SignInUser.create(email, "teatpassword");

            //when
            Throwable throwable = catchThrowable(() ->
                    authService.userSignIn(signInUser));

            //then
            assertThat(throwable).isInstanceOf(AuthException.class);
            assertThat(throwable.getMessage()).isEqualTo(AuthError.INVALID_PASSWORD.getMessage());
        }
    }

    @Nested
    @DisplayName("스토어 스태프 로그인 테스트")
    public class StoreStaffSignIn {

        StoreStaffEntity storeStaff;
        String password = "testpassword";
        String encodePassword = passwordEncoder.encode(password);
        String uuid = UUID.randomUUID().toString().substring(0, 11);

        @BeforeEach
        public void init() {

            StoreEntity storeEntity = storeJpaRepository.save(
                    StoreEntity.builder()
                            .brand(uuid)
                            .name(uuid)
                            .address("경기 성남시 중원구 성남대로 997")
                            .addressDetail("정문")
                            .postalCode("13437")
                            .businessLicenseNumber(uuid)
                            .approveType(ApproveType.APPROVE)
                            .build()
            );
            storeStaff = storeStaffJpaRepository.save(
                    StoreStaffEntity.builder()
                            .storeId(storeEntity.getStoreId())
                            .name(uuid)
                            .email(uuid + "@gmail.com")
                            .password(encodePassword)
                            .nickname(uuid)
                            .phoneNumber("99988887777")
                            .storeStaffRole(StoreStaffRole.ROLE_STORE_OWNER)
                            .build()
            );
        }

        @Test
        @Transactional
        @DisplayName("로그인이 성공하여 토큰 발급에 성공한다.")
        public void successUserSignIn() throws Exception {
            //given
            String email = storeStaff.getEmail();
            SignInUser signInUser = SignInUser.create(email, password);

            //when
            String token = authService.storeSignIn(signInUser);

            //then
            assertThat(token).isNotBlank();
        }

        @Test
        @Transactional
        @DisplayName("올바르지 않은 비밀번호인 경우 로그인에 실패한다.")
        public void failUserSignIn() throws Exception {
            //given
            String email = storeStaff.getEmail();
            SignInUser signInUser = SignInUser.create(email, "teatpassword");

            //when
            Throwable throwable = catchThrowable(() ->
                    authService.storeSignIn(signInUser));

            //then
            assertThat(throwable).isInstanceOf(AuthException.class);
            assertThat(throwable.getMessage()).isEqualTo(AuthError.INVALID_PASSWORD.getMessage());
        }
    }
}
