package com.cozybinarybase.accountstopthestore.model.challenge.dto;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteLinkResponseDto {

  private Long groupId;
  private String inviteLink;

  public static InviteLinkResponseDto fromEntity(ChallengeGroupEntity ChallengeGroup) {
    return InviteLinkResponseDto.builder()
        .groupId(ChallengeGroup.getId())
        .inviteLink(ChallengeGroup.getInviteLink())
        .build();
  }
}
