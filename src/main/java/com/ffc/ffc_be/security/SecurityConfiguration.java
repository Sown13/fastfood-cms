package com.ffc.ffc_be.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String PUBLIC_ENDPOINT[] = {"swagger-ui/**", "/v3/api-docs/**", "/login", "/check-authority"};

    private final String BOSS_ENDPOINT[] = {"/user-info/create-user", "/user-info/**", "/**"};

    private final String ACCOUNTANT_ENDPOINT[] = {"/check-authority/accountant"};

    private final String MARKETING_ENDPOINT[] = {"/check-authority/marketing"};

    private final String MANAGER_ENDPOINT[] = {"/check-authority/manager", "/user-info/create-user"};

    private final String KITCHEN_ENDPOINT[] = {"/check-authority/kitchen"};

    private final String SALE_ENDPOINT[] = {"/check-authority/sale"};

    private final String SHIPPER_ENDPOINT[] = {"/check-authority/shipper"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_ENDPOINT).permitAll())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(ACCOUNTANT_ENDPOINT).hasAnyRole("BOSS", "ACCOUNTANT"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(MARKETING_ENDPOINT).hasAnyRole("BOSS", "MARKETING"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(SALE_ENDPOINT).hasAnyRole("BOSS", "SALE", "MANAGER"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(SHIPPER_ENDPOINT).hasAnyRole("BOSS", "SHIPPER", "MANAGER"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(KITCHEN_ENDPOINT).hasAnyRole("BOSS", "KITCHEN"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(MANAGER_ENDPOINT).hasAnyRole("BOSS", "MANAGER"))
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(BOSS_ENDPOINT).hasRole("BOSS"))
                .authorizeHttpRequests(request -> request
                        .anyRequest().authenticated())
                .exceptionHandling(
                        e -> e.accessDeniedHandler(
                                        (request, response, accessDeniedException) -> response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // return 401 status for better understanding
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
