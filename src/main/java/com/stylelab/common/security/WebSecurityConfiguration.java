package com.stylelab.common.security;

import com.stylelab.common.security.exception.CustomAccessDeniedHandler;
import com.stylelab.common.security.exception.CustomAuthenticationEntryPoint;
import com.stylelab.common.security.filter.JwtAuthenticationFilter;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.user.constant.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화를 수행합니다.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    /**
     * 정적 자원(Resource)에 대해서 인증된 사용자가  정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드이다.
     * 정적 자원에 대해서 Security를 적용하지 않음으로 설정
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .requestMatchers(
                                        "/**/users/signup",
                                        "/**/users/check-email",
                                        "/**/users/check-nickname"
                                ).permitAll()
                                .requestMatchers(
                                        "/**/auth/users/signin",
                                        "/**/auth/stores/signin"
                                ).permitAll()
                                .requestMatchers(
                                        "/**/categories",
                                        "/**/categories/{productCategoryPath}"
                                ).permitAll()
                                .requestMatchers(
                                        "/**/stores/apply"
                                ).permitAll()
                                .requestMatchers(
                                        "/**/products",
                                        "/**/products/{productId}"
                                ).permitAll()
                                .requestMatchers(
                                        "/healthCheck"
                                ).permitAll()
                                .requestMatchers( "/**/users/deliveries").hasRole(UserRole.ROLE_USER.getRole())
                                .requestMatchers( "/**/stores/**").hasAnyRole(
                                        StoreStaffRole.ROLE_STORE_OWNER.getRole(),
                                        StoreStaffRole.ROLE_STORE_STAFF.getRole()
                                )
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
