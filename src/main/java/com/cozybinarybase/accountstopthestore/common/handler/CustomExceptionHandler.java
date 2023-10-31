package com.cozybinarybase.accountstopthestore.common.handler;

import com.cozybinarybase.accountstopthestore.common.dto.ResponseDto;
import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomApiException;
import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomApiException.class)
  public ResponseEntity<?> apiException(CustomApiException e) {
    log.error(e.getMessage());
    return new ResponseEntity<>(
        new ResponseDto<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<?> validationApiException(CustomValidationException e) {
    log.error(e.getMessage());
    return new ResponseEntity<>(
        new ResponseDto<>(false, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
  }
}
