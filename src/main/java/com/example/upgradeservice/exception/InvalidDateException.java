package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidDateException extends Error{

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> InvalidDateException(InvalidDateException invalidDateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("date is incorrect!!!");
    }
}
