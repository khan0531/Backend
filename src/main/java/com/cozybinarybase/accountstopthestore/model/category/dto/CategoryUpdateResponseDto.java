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
public class CategoryUpdateResponseDto {

  private Long id;
  private String categoryType;
  private String categoryName;

  public static CategoryUpdateResponseDto fromEntity(CategoryEntity categoryEntity) {
    return CategoryUpdateResponseDto.builder()
        .id(categoryEntity.getId())
        .categoryType(categoryEntity.getType().getValue())
        .categoryName(categoryEntity.getName())
        .build();
  }
}
