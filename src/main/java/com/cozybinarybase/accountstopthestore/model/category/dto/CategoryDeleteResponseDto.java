package com.cozybinarybase.accountstopthestore.model.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryDeleteResponseDto {

  private Long categoryId;
}
