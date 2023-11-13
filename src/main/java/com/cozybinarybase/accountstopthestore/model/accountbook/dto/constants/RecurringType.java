package com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RecurringType {
  DAILY("매일"),
  WEEKDAYS("주중"),
  WEEKENDS("주말"),
  WEEKLY("매주"),
  BIWEEKLY("2주마다"),
  FOUR_WEEKLY("4주마다"),
  MONTHLY("매월"),
  MONTH_END("월말"),
  BIMONTHLY("2개월마다"),
  QUARTERLY("3개월마다"),
  FOUR_MONTHLY("4개월마다"),
  SEMIANNUALLY("6개월마다"),
  ANNUALLY("1년마다");

  private final String value;

  @JsonValue
  public String toValue() {
    return this.value;
  }
}
