package com.example.roulette.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.roulette.entity.DuplicateUserException;
import com.example.roulette.entity.UserEntity;
import com.example.roulette.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserEntity registerUser(String name, String password) {
        // 既にユーザIDがある場合はエラーを出力する
        Optional<UserEntity> existingUser = userRepo.findByName(name);
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("ユーザ名:" + name + "は既に使用されています");
        }
        String encodedPass = passwordEncoder.encode(password);
        UserEntity newUser = UserEntity
                .builder()
                .name(name)
                .password(encodedPass)
                .roles(Collections.singleton("ROLE_USER"))
                // .roles(Collections.singleton("ROLE_ADMIN"))
                .build();
        return userRepo.save(newUser);

    }

    public UserEntity getUserByUsername(String name) {
        return userRepo.findByName(name).get();
    }

}
