package com.bgh.myopeninvoice.common.controller;

import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({InvalidDataException.class})
  @ResponseBody
  ResponseEntity<?> handleInvalidDataException(InvalidDataException ex) {
    return Utils.getErrorResponse(List.class, ex, ex.getErrors());
  }

  @ExceptionHandler({Throwable.class})
  @ResponseBody
  ResponseEntity<?> handleThrowableException(Throwable ex) {
    return Utils.getErrorResponse(
        String.class, ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({AuthenticationException.class})
  @ResponseBody
  ResponseEntity<?> handleAuthorizationException(Throwable ex) {
    return Utils.getErrorResponse(
            String.class, ex, ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  @ResponseBody
  public ResponseEntity<?> handleNoHandlerFoundException(Exception ex) {
    return Utils.getErrorResponse(String.class, ex, ex.getMessage(), HttpStatus.NOT_FOUND);
  }

}
