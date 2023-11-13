package com.cozybinarybase.accountstopthestore.model.accountbook.exception;

public class AccountBookNotValidException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "찾을 수 없는 가계부 번호입니다.";

  public AccountBookNotValidException() {
    super(DEFAULT_MESSAGE);
  }

  public AccountBookNotValidException(String message) {
    super(message);
  }
}