package com.cozybinarybase.accountstopthestore.common.handler;

import com.cozybinarybase.accountstopthestore.common.annotation.FailName;
import com.cozybinarybase.accountstopthestore.common.dto.ApiResponse;
import com.cozybinarybase.accountstopthestore.common.dto.FailDetailDto;
import com.cozybinarybase.accountstopthestore.common.handler.exception.AssetNotFoundException;
import com.cozybinarybase.accountstopthestore.common.handler.exception.MemberMismatchException;
import com.cozybinarybase.accountstopthestore.common.handler.exception.MemberNotFoundException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(MemberMismatchException.class)
  public ApiResponse<FailDetailDto> memberMismatchException(MemberMismatchException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.nestedError("member").addFieldError("memberId", e.getMessage());

    return ApiResponse.fail(failDetail);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(MemberNotFoundException.class)
  public ApiResponse<FailDetailDto> memberNotFoundException(MemberNotFoundException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.nestedError("member").addFieldError("memberId", e.getMessage());

    return ApiResponse.fail(failDetail);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(AssetNotFoundException.class)
  public ApiResponse<FailDetailDto> assetNotFoundException(AssetNotFoundException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.nestedError("asset").addFieldError("assetId", e.getMessage());

    return ApiResponse.fail(failDetail);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    Object target = bindingResult.getTarget();

    FailName failNameAnnotation = target.getClass().getAnnotation(FailName.class);
    String entityName;
    if (failNameAnnotation != null) {
      entityName = failNameAnnotation.value();
    } else {
      entityName = target.getClass().getSimpleName().toLowerCase();
    }

    FailDetailDto rootError = new FailDetailDto();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String[] fields = fieldError.getField().split("\\.");
      FailDetailDto currentError = rootError;

      for (int i = 0; i < fields.length - 1; i++) {
        currentError = currentError.nestedError(fields[i]);
      }
      currentError.addFieldError(fields[fields.length - 1], fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest()
        .body(ApiResponse.fail(Map.of(entityName, rootError.getErrors())));
  }
}
