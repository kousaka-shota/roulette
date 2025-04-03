package com.example.roulette.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.roulette.entity.UserEntity;
import com.example.roulette.service.UserService;
import com.example.roulette.util.JwtUtil;
import com.example.roulette_api.controller.AuthApi;
import com.example.roulette_api.controller.model.AuthRequestBody;
import com.example.roulette_api.controller.model.AuthResponseBody;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userSer;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // @PostMapping("/register")
    public ResponseEntity<String> register(AuthRequestBody requestBody) {
        userSer.registerUser(requestBody.getName(), requestBody.getPassword());
        return ResponseEntity.ok("作成されました");
    }

    public ResponseEntity<AuthResponseBody> login(AuthRequestBody requestBody) {
        try {
            var authResult = authenticationManager.authenticate(
                    // springで定義されている以下のインスタンスを生成。実際にユーザの存在やPWのあっているか認証を行う
                    new UsernamePasswordAuthenticationToken(
                            requestBody.getName(), requestBody.getPassword()));
            UserEntity user = userSer.getUserByUsername(requestBody.getName());
            String token = jwtUtil.generateToken(user.getName(), user.getRoles());

            return ResponseEntity.ok().body(new AuthResponseBody(token));

        } catch (BadCredentialsException e) {
            throw new RuntimeException("無効な認証情報");
        }

    }

}
