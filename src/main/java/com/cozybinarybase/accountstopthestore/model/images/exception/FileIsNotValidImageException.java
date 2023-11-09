package com.cozybinarybase.accountstopthestore.model.images.exception;

public class FileIsNotValidImageException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "이미지 파일이 아닙니다.";

  public FileIsNotValidImageException() {
    super(DEFAULT_MESSAGE);
  }

  public FileIsNotValidImageException(String message) {
    super(message);
  }
}
