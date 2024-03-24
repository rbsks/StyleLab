package com.stylelab.user.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("회원 테이블에 이메일이 존재하는지 확인하는 쿼리")
    public void existsByEmail() {
        userJpaRepository.existsByEmail("test@gmail.com");
    }

    @Test
    @DisplayName("회원 테이블에 닉네임이 존재하는지 확인하는 쿼리")
    public void existsByNickname() {
        userJpaRepository.existsByNickname("coby");
    }
}
