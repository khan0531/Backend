package com.cozybinarybase.accountstopthestore.model.accountbook.dto;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TransactionStatisticsResponse {

  private LocalDate startDate;
  private LocalDate endDate;
  private TransactionType transactionType;
  private List<StatisticsData> statisticsData;
}