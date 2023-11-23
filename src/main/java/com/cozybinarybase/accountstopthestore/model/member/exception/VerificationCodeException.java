package com.cozybinarybase.accountstopthestore.model.member.exception;

public class VerificationCodeException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "찾을 수 없는 코드입니다.";

  public VerificationCodeException() {
    super(DEFAULT_MESSAGE);
  }

  public VerificationCodeException(String message) {
    super(message);
  }
}
