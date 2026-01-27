package com.example.itau_backend.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.itau_backend.api.exception.exceptions.IllegalTransactionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalTransactionException.class)
  ResponseEntity<Void> handleIllegalTransaction() {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(NoResourceFoundException.class)
  ResponseEntity<Void> handleResourceNotFound(NoResourceFoundException e) {
    return ResponseEntity.notFound().build();

  }
}
