package com.cozybinarybase.accountstopthestore.model.category.exception;

public class CategoryNotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "찾을 수 없는 카테고리 번호입니다.";

  public CategoryNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public CategoryNotFoundException(String message) {
    super(message);
  }
}