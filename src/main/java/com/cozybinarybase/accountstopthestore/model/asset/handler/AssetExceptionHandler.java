package com.cozybinarybase.accountstopthestore.model.asset.handler;

import com.cozybinarybase.accountstopthestore.common.dto.ApiResponse;
import com.cozybinarybase.accountstopthestore.common.dto.FailDetailDto;
import com.cozybinarybase.accountstopthestore.model.asset.handler.exception.AssetNotFoundException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.cozybinarybase.accountstopthestore.model.asset")
public class AssetExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(AssetNotFoundException.class)
  public ResponseEntity<ApiResponse<?>> assetNotFoundException(AssetNotFoundException e) {
    log.error(e.getMessage());

    FailDetailDto failDetail = new FailDetailDto();
    failDetail.addFieldError("assetId", e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.fail(Map.of("asset", failDetail.getErrors())));
  }
}
