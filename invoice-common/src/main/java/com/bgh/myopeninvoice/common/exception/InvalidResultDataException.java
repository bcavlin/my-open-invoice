package com.bgh.myopeninvoice.common.exception;

public class InvalidResultDataException extends RuntimeException {

  public InvalidResultDataException() {
    super();
  }

  public InvalidResultDataException(String message) {
    super(message);
  }

  public InvalidResultDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidResultDataException(Throwable cause) {
    super(cause);
  }

  protected InvalidResultDataException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
