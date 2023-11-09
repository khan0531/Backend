package com.cozybinarybase.accountstopthestore.model.challenge.domain;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.MemberGroupEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroup {

  private Long id;
  private Long memberId;
  private Long groupId;
  private Long savedAmount;

  public static MemberGroup fromEntity(MemberGroupEntity memberGroupEntity) {
    return MemberGroup.builder()
        .id(memberGroupEntity.getId())
        .memberId(memberGroupEntity.getMember().getId())
        .groupId(memberGroupEntity.getChallengeGroup().getId())
        .savedAmount(memberGroupEntity.getSavedAmount())
        .build();
  }

  public static MemberGroup create(Long memberId, Long groupId) {
    return MemberGroup.builder()
        .memberId(memberId)
        .groupId(groupId)
        .savedAmount(0L)
        .build();
  }

  public MemberGroupEntity toEntity() {
    return MemberGroupEntity.builder()
        .member(MemberEntity.builder().id(memberId).build())
        .group(ChallengeGroupEntity.builder().id(groupId).build())
        .savedAmount(savedAmount)
        .build();
  }
}
