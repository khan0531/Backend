package com.cozybinarybase.accountstopthestore.model.budget.dto;

import com.cozybinarybase.accountstopthestore.model.budget.persist.entity.BudgetEntity;
import lombok.Data;

@Data
public class BudgetResponseDto {
  private Long id;
  private String yearMonth;
  private Long budget;

  public static BudgetResponseDto fromEntity(BudgetEntity entity) {
    BudgetResponseDto dto = new BudgetResponseDto();
    dto.setId(entity.getId());
    dto.setYearMonth(entity.getYearMonth().toString());
    dto.setBudget(entity.getBudget());
    return dto;
  }
}
