package com.cozybinarybase.accountstopthestore.model.member.dto.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {
  EMAIL("EMAIL"), GOOGLE("GOOGLE");

  private final String authType;
}
