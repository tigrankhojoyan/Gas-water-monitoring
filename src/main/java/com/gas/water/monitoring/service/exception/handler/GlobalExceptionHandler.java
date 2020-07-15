package com.gas.water.monitoring.service.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.gas.water.monitoring.exception.DataNotFoundException;
import com.gas.water.monitoring.exception.DataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Intended for global exception handling
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataValidationException.class, JsonParseException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, DataNotFoundException.class})
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
