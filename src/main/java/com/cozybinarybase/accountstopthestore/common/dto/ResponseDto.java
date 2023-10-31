package com.cozybinarybase.accountstopthestore.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

  private final boolean code; // true 성공, false 실패
  private final String message;
  private final T data;
}
