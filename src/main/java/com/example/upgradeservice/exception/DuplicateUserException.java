package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DuplicateUserException extends Error {

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String>DuplicateUserException(DuplicateUserException duplicateUserException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplication is occured");
	}
}
