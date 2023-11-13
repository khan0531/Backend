package com.cozybinarybase.accountstopthestore.model.category.dto.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryType {

  INCOME("수입"), SPENDING("지출");

  private final String value;

  @JsonValue
  public String toValue() {
    return this.value;
  }
}
