package com.cozybinarybase.accountstopthestore.common.handler.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {

  private final Map<String, String> errorMap;

  public CustomValidationException(String message, Map<String, String> errorMap) {
    super(message);
    this.errorMap = errorMap;
  }
}