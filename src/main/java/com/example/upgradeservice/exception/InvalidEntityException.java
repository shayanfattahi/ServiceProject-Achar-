package com.example.upgradeservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidEntityException extends Error {

	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<String> InvalidEntityException(InvalidEntityException invalidEntityException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid entity validation");
	}
}
