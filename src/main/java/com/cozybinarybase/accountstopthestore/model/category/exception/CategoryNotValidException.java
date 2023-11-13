package com.cozybinarybase.accountstopthestore.model.category.exception;

public class CategoryNotValidException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "찾을 수 없는 카테고리 번호입니다.";

  public CategoryNotValidException() {
    super(DEFAULT_MESSAGE);
  }

  public CategoryNotValidException(String message) {
    super(message);
  }
}