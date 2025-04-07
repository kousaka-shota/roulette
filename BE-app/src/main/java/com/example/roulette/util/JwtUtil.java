package com.example.roulette.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMillis = 3600000; // 1時間

    // nameとroleを受け取ってJWTTokenを生成
    public String generateToken(String username, Set<String> roles) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // roleをセット
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMillis)) // 有効期限をセット
                .signWith(key) // 署名する
                .compact();
    }

    // tokenが有効かどうか検証する
    public Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 改ざんされていないか確認するのに使用する鍵の指定
                .build()
                .parseClaimsJws(token) // tokenの解析して改ざんあったり有効期限が切れてたらエラーをthrow
                .getBody(); // ユーザ名やrole等（claim）の取得
    }
}
