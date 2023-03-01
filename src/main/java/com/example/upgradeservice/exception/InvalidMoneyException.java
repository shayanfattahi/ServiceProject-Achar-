package com.example.upgradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidMoneyException extends Error {

    @ExceptionHandler(InvalidMoneyException.class)
    public ResponseEntity<String> InvalidMoneyException(InvalidMoneyException invalidDateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Money is incorrect!!!");
    }
}
