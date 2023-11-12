package com.cozybinarybase.accountstopthestore.model.accountbook.dto;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.RecurringType;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AccountBookSaveRequestDto {

  @NotBlank(message = "카테고리명을 입력해주시길 바랍니다.")
  private String categoryName;

  @NotBlank(message = "자산 이름을 입력해주시길 바랍니다.")
  private String assetName;

  @Positive(message = "양수의 값만 입력할 수 있습니다.")
  private Long amount;

  @NotNull(message = "거래 유형을 입력해주시길 바랍니다.")
  private TransactionType transactionType;

  @NotBlank(message = "사용처를 입력해주시길 바랍니다.")
  private String transactionDetail;

  @NotNull(message = "사용 일자를 입력해주시길 바랍니다.")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime transactedAt;

  private List<Long> imageIds;
  private String address;
  private String memo;
  private RecurringType recurringType;
  private Boolean isInstallment;
}
