package com.cozybinarybase.accountstopthestore.model.challenge.dto;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.MemberGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroupResponseDto {

  private String groupName;
  private String memberName;
  private Long savedAmount;

  public static MemberGroupResponseDto fromEntity(MemberGroupEntity memberGroupEntity) {
    return MemberGroupResponseDto.builder()
        .groupName(memberGroupEntity.getChallengeGroup().getName())
        .memberName(memberGroupEntity.getMember().getName())
        .savedAmount(memberGroupEntity.getSavedAmount())
        .build();
  }
}
