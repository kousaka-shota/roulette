package com.example.roulette.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.roulette.util.JwtUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 各APIに対するリクエストが来るとまずは以下の関数でトークンの認証が行われる
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // HeaderのAuthorizationに含まれるトークンを取得
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Headerが存在し、Bearerで始まるかどうか確認
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            // Bearer以降のtokenを取得
            String token = header.substring(7);
            try {
                // tokenの検証（改ざんや有効期限切れがないか）
                Claims claims = jwtUtil.validateToken(token);
                // Subjectにnameが含まれるため
                String username = claims.getSubject();

                // roleを取得
                @SuppressWarnings("unchecked")
                List<String> rolesList = claims.get("roles", List.class);
                Set<String> roles = new HashSet<>(rolesList);
                // 追加: 抽出したロールをログ出力
                logger.debug("Extracted roles from JWT: " + roles);
                // usernameとroleを設定
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        roles.stream()
                                // .map(role -> "ROLE_" + role)
                                // .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(Collectors.toList()));
                // httpリクエストの詳細情報（セッションIDやクライアントのIPアドレス）をセット
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("Authenticated user: " + username + " with roles: " + authentication);
                // application全体で認証情報を使えるようにする
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                logger.error("Invalid JWT token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
                // トークンが無効の場合、認証情報をクリア
                SecurityContextHolder.clearContext();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
