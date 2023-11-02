package com.cozybinarybase.accountstopthestore.model.asset.dto.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AssetType {

  MONEY("현금"),
  BANK("은행"),
  CARD("카드");

  private final String value;
}
