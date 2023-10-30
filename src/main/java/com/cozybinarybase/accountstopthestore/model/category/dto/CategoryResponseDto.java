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

  private Long id;
  private String categoryName;
  private Long categoryOwnerId;

  public static CategoryResponseDto fromEntity(CategoryEntity categoryEntity) {
    return CategoryResponseDto.builder()
        .id(categoryEntity.getId())
        .categoryName(categoryEntity.getName())
        .categoryOwnerId(categoryEntity.getMember().getId())
        .build();
  }
}
