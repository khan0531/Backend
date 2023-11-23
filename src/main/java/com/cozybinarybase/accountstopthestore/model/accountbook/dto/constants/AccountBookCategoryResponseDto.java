package com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountBookCategoryResponseDto {

  private List<String> categories;

  public static AccountBookCategoryResponseDto of(List<String> categories) {
    return AccountBookCategoryResponseDto.builder()
        .categories(categories)
        .build();
  }
}
