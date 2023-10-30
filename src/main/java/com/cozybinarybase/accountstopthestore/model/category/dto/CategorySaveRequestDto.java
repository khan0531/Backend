package com.cozybinarybase.accountstopthestore.model.category.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategorySaveRequestDto {

  @NotBlank(message = "카테고리명을 입력해주세요.")
  private String categoryName;
}
