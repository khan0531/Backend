package com.cozybinarybase.accountstopthestore.common.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class FailDetailDto {

  private Map<String, Object> errors = new HashMap<>();

  public void addFieldError(String key, Object value) {
    errors.put(key, value);
  }

  public FailDetailDto nestedError(String key) {
    FailDetailDto nested = new FailDetailDto();
    errors.put(key, nested.getErrors());
    return nested;
  }
}
