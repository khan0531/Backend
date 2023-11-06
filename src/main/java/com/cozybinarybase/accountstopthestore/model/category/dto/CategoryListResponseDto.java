package com.cozybinarybase.accountstopthestore.model.category.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryListResponseDto {

  private List<CategoryResponseDto> categories;

  public static CategoryListResponseDto of(List<CategoryResponseDto> categories) {
    return CategoryListResponseDto.builder()
        .categories(categories)
        .build();
  }
}
