package com.cozybinarybase.accountstopthestore.model.category.dto.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryType {

  INCOME("수입"), SPENDING("지출");

  private final String value;
}
