package com.cozybinarybase.accountstopthestore.common.handler;

import com.cozybinarybase.accountstopthestore.common.dto.ApiResponse;
import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomApiException;
import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CustomApiException.class)
  public ApiResponse<?> apiException(CustomApiException e) {
    log.error(e.getMessage());

    return ApiResponse.fail(e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CustomValidationException.class)
  public ApiResponse<?> validationApiException(CustomValidationException e) {
    log.error(e.getMessage());

    return ApiResponse.error(e.getMessage(), e.getErrorMap());
  }
}
