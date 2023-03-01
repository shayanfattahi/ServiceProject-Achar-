package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidOutPutException extends Error{

    @ExceptionHandler(InvalidOutPutException.class)
    public ResponseEntity<String> InvalidOutPutException(InvalidOutPutException invalidDateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("output is null!!!");
    }
}
