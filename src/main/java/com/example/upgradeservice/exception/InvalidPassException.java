package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidPassException extends Error{

    @ExceptionHandler(InvalidPassException.class)
    public ResponseEntity<String> InvalidPassException(InvalidPassException invalidDateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pass is incorrect!!!");
    }
}
