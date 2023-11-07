package com.cozybinarybase.accountstopthestore.model.asset.handler.exception;

public class AssetNotValidException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "찾을 수 없는 자산 번호입니다.";

  public AssetNotValidException() {
    super(DEFAULT_MESSAGE);
  }

  public AssetNotValidException(String message) {
    super(message);
  }
}