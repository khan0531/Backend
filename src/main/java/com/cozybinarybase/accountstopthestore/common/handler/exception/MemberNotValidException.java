package com.cozybinarybase.accountstopthestore.common.handler.exception;

public class MemberNotValidException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "회원 정보가 일치하지 않습니다.";

  public MemberNotValidException() {
    super(DEFAULT_MESSAGE);
  }

  public MemberNotValidException(String message) {
    super(message);
  }
}
