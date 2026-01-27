package com.example.itau_backend.api.exception.exceptions;

public class IllegalTransactionException extends RuntimeException {
  public IllegalTransactionException() {
    super();
  }

  public IllegalTransactionException(String message) {
    super(message);
  }
}
