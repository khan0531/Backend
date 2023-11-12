package com.cozybinarybase.accountstopthestore.common.handler;

import com.cozybinarybase.accountstopthestore.common.handler.exception.MemberNotValidException;
import com.cozybinarybase.accountstopthestore.model.accountbook.exception.AccountBookNotValidException;
import com.cozybinarybase.accountstopthestore.model.asset.exception.AssetNotValidException;
import com.cozybinarybase.accountstopthestore.model.category.exception.CategoryNotValidException;
import com.cozybinarybase.accountstopthestore.model.images.exception.FileIsNotValidImageException;
import com.cozybinarybase.accountstopthestore.model.images.exception.ImageNotValidException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MemberNotValidException.class)
  public ResponseEntity<?> handleMemberNotValidException(MemberNotValidException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(errorDetails);
  }

  @ExceptionHandler(AssetNotValidException.class)
  public ResponseEntity<?> handleAssetNotValidException(AssetNotValidException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }

  @ExceptionHandler(CategoryNotValidException.class)
  public ResponseEntity<?> handleCategoryNotValidException(CategoryNotValidException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }

  @ExceptionHandler(AccountBookNotValidException.class)
  public ResponseEntity<?> handleAccountBookNotValidException(AccountBookNotValidException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }

  @ExceptionHandler(ImageNotValidException.class)
  public ResponseEntity<?> handleImageNotValidException(ImageNotValidException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }

  @ExceptionHandler(FileIsNotValidImageException.class)
  public ResponseEntity<?> handleFileIsNotValidImageException(FileIsNotValidImageException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage,
            (existingValue, newValue) -> existingValue
        ));
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
    log.error(e.getMessage());

    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", e.getMessage());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorDetails);
  }
}
