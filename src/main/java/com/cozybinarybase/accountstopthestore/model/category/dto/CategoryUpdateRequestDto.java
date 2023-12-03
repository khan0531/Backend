package com.cozybinarybase.accountstopthestore.model.category.dto;

import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryUpdateRequestDto {

  @NotNull(message = "카테고리명을 입력해주세요.")
  private String categoryName;

  @NotNull(message = "카테고리 유형을 입력해주세요.")
  private CategoryType categoryType;
}
