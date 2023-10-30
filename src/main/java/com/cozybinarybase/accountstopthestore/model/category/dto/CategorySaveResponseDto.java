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
public class CategorySaveResponseDto {

  private Long id;
  private String categoryName;

  public static CategorySaveResponseDto fromEntity(CategoryEntity categoryEntity) {
    return CategorySaveResponseDto.builder()
        .id(categoryEntity.getId())
        .categoryName(categoryEntity.getName())
        .build();
  }
}
