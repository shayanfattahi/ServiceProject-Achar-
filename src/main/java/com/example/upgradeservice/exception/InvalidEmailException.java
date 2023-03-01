package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidEmailException extends Error{

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> InvalidEmailException(InvalidEmailException invalidDateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is incorrect!!!");
    }
}
