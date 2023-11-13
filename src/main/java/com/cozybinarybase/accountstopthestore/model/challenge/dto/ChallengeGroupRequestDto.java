package com.cozybinarybase.accountstopthestore.model.challenge.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChallengeGroupRequestDto {

  private String name;

  private String description;

  private Long targetAmount;

  private Long maxMembers;

  private LocalDateTime startAt;

  private LocalDateTime endAt;
}
