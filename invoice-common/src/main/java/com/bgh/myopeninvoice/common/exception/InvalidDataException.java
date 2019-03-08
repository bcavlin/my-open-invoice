package com.bgh.myopeninvoice.common.exception;

import java.util.List;

public class InvalidDataException extends RuntimeException {

  private List<String> errors;

  public InvalidDataException() {
    super();
  }

  public InvalidDataException(String message) {
    super(message);
  }

  public InvalidDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataException(Throwable cause) {
    super(cause);
  }

  public InvalidDataException(String message, List<String> errors) {
    super(message);
    this.errors = errors;
  }

  protected InvalidDataException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public List<String> getErrors() {
    return errors;
  }

}
