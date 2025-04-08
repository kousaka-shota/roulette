package com.example.roulette.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.roulette.entity.UserEntity;
import com.example.roulette.filter.JwtAuthenticationFilter;
import com.example.roulette.service.UserService;

@Configuration
@EnableWebSecurity // spring securityの設定をOnにする
@EnableMethodSecurity // 各メソッドにセキュリティの制御を有効にする
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final PasswordEncoder passwordEncoder;
        private final UserService userSer;

        @Bean
        public UserDetailsService userDetailsService() {
                return username -> {
                        UserEntity user = userSer.getUserByUsername(username);
                        // ↑で取得したuserをもとにspringのuserオブジェクトを生成する
                        String[] rolesWithPrefix = user.getRoles().stream()
                                        .map(role -> "ROLE_" + role).toArray(String[]::new);
                        return org.springframework.security.core.userdetails.User.builder()
                                        .username(user.getName())
                                        .password(user.getPassword())
                                        .authorities(rolesWithPrefix)
                                        .build();
                };
        }

        @Bean
        public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                // DBから取得したuserDetailsServiceをセットしユーザの認証をするためにセットする（実際にはまだ認証はしてない）
                authProvider.setUserDetailsService(userDetailsService);
                // encodeする方法をセット
                authProvider.setPasswordEncoder(passwordEncoder);
                return new ProviderManager(authProvider);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // APIなのでCSRFは無効化
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**",
                                                                "/h2-console/**") // これらのPathにマッチするAPIは認証が不要であることを定義
                                                .permitAll()
                                                .anyRequest().authenticated()) // 上記以外のpathは全て認証が必要であることを定義
                                .headers(headers -> headers
                                                .frameOptions(frameOptions -> frameOptions.sameOrigin()))
                                // JWTはステートレスなので、セッションポリシーをステートレスに設定
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                                // JWTトークンを含むリクエストは必ずjwtAuthenticationFilterで処理されてから行われる（必ず認証が行われる）
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
