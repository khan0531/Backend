package com.cozybinarybase.accountstopthestore.model.challenge.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChallengeGroupRequestDto {

  private String name;

  private String description;

  private Long targetAmount;

  private Long maxMembers;

  private LocalDate startAt;

  private LocalDate endAt;
}
