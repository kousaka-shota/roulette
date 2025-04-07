package com.example.roulette.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.roulette.entity.DuplicateUserException;
import com.example.roulette.service.ThemeService.ResourceNotFoundException;
import com.example.roulette_api.controller.model.BadRequestError;
import com.example.roulette_api.controller.model.ThemeNotFound;

@RestControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BadRequestError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        BadRequestError error = new BadRequestError();
        error.setTitle("Bad Request");
        error.setDetail("データベース制約違反です。");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ThemeNotFound> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ThemeNotFound error = new ThemeNotFound();
        error.setTitle("Bad Request");
        error.setMessage("ThemeIdが存在しません");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("無効な認証情報");
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
