package com.cozybinarybase.accountstopthestore.model.images.exception;

public class ImageNotValidException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "이미지 파일이 없거나 소유주가 아닙니다.";

  public ImageNotValidException() {
    super(DEFAULT_MESSAGE);
  }

  public ImageNotValidException(String message) {
    super(message);
  }
}
