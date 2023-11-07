package com.cozybinarybase.accountstopthestore.model.category.dto;

import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryResponseDto {

  private Long categoryId;
  private String categoryType;
  private String categoryName;

  public static CategoryResponseDto fromEntity(CategoryEntity categoryEntity) {
    return CategoryResponseDto.builder()
        .categoryId(categoryEntity.getId())
        .categoryType(categoryEntity.getType().getValue())
        .categoryName(categoryEntity.getName())
        .build();
  }
}
