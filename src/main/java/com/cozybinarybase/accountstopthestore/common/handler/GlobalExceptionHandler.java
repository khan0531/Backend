package com.cozybinarybase.accountstopthestore.common.handler;

import com.cozybinarybase.accountstopthestore.common.dto.ApiResponse;
import com.cozybinarybase.accountstopthestore.common.dto.FailDetailDto;
import com.cozybinarybase.accountstopthestore.common.exception.MemberMismatchException;
import com.cozybinarybase.accountstopthestore.common.exception.MemberNotFoundException;
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
public class GlobalExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(MemberMismatchException.class)
  public ApiResponse<FailDetailDto> memberMismatchException(MemberMismatchException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.nestedError("member").addFieldError("memberId", e.getMessage());

    return ApiResponse.fail(failDetail);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(MemberNotFoundException.class)
  public ApiResponse<FailDetailDto> memberNotFoundException(MemberNotFoundException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.nestedError("member").addFieldError("memberId", e.getMessage());

    return ApiResponse.fail(failDetail);
  }

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
