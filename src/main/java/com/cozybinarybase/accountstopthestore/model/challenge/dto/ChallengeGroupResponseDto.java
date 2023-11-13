package com.cozybinarybase.accountstopthestore.model.challenge.dto;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChallengeGroupResponseDto {

  private String name;

  private String description;

  private Long targetAmount;

  private Long maxMembers;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  private String adminEmail;

  public static ChallengeGroupResponseDto fromEntity(ChallengeGroupEntity challengeGroupEntity) {
    return ChallengeGroupResponseDto.builder()
        .name(challengeGroupEntity.getName())
        .description(challengeGroupEntity.getDescription())
        .targetAmount(challengeGroupEntity.getTargetAmount())
        .maxMembers(challengeGroupEntity.getMaxMembers())
        .startAt(challengeGroupEntity.getStartAt())
        .endAt(challengeGroupEntity.getEndAt())
        .adminEmail(challengeGroupEntity.getAdmin().getEmail())
        .build();
  }
}
