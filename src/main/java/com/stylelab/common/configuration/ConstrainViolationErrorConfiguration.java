package com.stylelab.common.configuration;

import com.stylelab.auth.exception.AuthError;
import com.stylelab.common.exception.ConstraintViolationErrorMap;
import com.stylelab.common.exception.ConstraintViolationErrorType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.store.exception.StoreError;
import com.stylelab.user.exception.UserError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.stylelab.common.exception.ConstraintViolationErrorType.AUTH_ERROR;
import static com.stylelab.common.exception.ConstraintViolationErrorType.PRODUCT_ERROR;
import static com.stylelab.common.exception.ConstraintViolationErrorType.STORE_ERROR;
import static com.stylelab.common.exception.ConstraintViolationErrorType.USERS_ERROR;

@Configuration
@RequiredArgsConstructor
public class ConstrainViolationErrorConfiguration {

    @Bean
    public ConstraintViolationErrorMap serviceErrorMap() {
        Map<String, ConstraintViolationErrorType> errorHandlerMap = Map.of(
                UserError.class.getName(), USERS_ERROR,
                StoreError.class.getName(), STORE_ERROR,
                ProductError.class.getName(), PRODUCT_ERROR,
                AuthError.class.getName(), AUTH_ERROR
        );

        return ConstraintViolationErrorMap.builder()
                .errorHandlerMap(errorHandlerMap)
                .build();
    }
}
