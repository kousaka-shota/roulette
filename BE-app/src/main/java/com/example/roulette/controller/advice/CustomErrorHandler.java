package com.example.roulette.controller.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.roulette_api.controller.model.BadRequestError;

@RestControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BadRequestError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        BadRequestError error = new BadRequestError();
        error.setTitle("Bad Request");
        error.setDetail("データベース制約違反です。");
        
        return ResponseEntity.badRequest().body(error);
    }
}
