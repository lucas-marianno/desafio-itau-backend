package com.example.itau_backend.exception.exceptions;

public class IllegalTransactionException extends RuntimeException {
  public IllegalTransactionException() {
    super();
  }

  public IllegalTransactionException(String message) {
    super(message);
  }
}
