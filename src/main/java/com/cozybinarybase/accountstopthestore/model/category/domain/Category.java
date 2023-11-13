package com.cozybinarybase.accountstopthestore.model.category.domain;

import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Component
public class Category {

  private Long id;
  private String name;
  private CategoryType type;
  private Long memberId;

  public Category createCategory(CategorySaveRequestDto requestDto, Long memberId) {
    return Category.builder()
        .name(requestDto.getCategoryName())
        .type(requestDto.getCategoryType())
        .memberId(memberId)
        .build();
  }

  public void updateCategory(CategoryUpdateRequestDto requestDto) {
    if (requestDto.getCategoryName() != null) {
      this.name = requestDto.getCategoryName();
    }
    if (requestDto.getCategoryType() != null) {
      this.type = requestDto.getCategoryType();
    }
  }

  public CategoryEntity toEntity() {
    return CategoryEntity.builder()
        .id(this.id)
        .name(this.name)
        .type(this.type)
        .member(MemberEntity.builder().id(this.memberId).build())
        .build();
  }

  public static Category fromEntity(CategoryEntity categoryEntity) {
    return Category.builder()
        .id(categoryEntity.getId())
        .name(categoryEntity.getName())
        .type(categoryEntity.getType())
        .memberId(categoryEntity.getMember().getId())
        .build();
  }
}
