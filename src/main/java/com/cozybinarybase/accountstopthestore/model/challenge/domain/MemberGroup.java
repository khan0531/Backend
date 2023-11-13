package com.cozybinarybase.accountstopthestore.model.challenge.domain;

import com.cozybinarybase.accountstopthestore.model.challenge.dto.SavingMoneyRequestDto;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.MemberGroupEntity;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
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
  private Long challengeGroupId;
  private Long savedAmount;

  public static MemberGroup fromEntity(MemberGroupEntity memberGroupEntity) {
    return MemberGroup.builder()
        .id(memberGroupEntity.getId())
        .memberId(memberGroupEntity.getMember().getId())
        .challengeGroupId(memberGroupEntity.getChallengeGroup().getId())
        .savedAmount(memberGroupEntity.getSavedAmount())
        .build();
  }

  public static MemberGroup create(ChallengeGroup group, Member member) {
    return MemberGroup.builder()
        .memberId(member.getId())
        .challengeGroupId(group.getId())
        .savedAmount(0L)
        .build();
  }

  public MemberGroupEntity toEntity() {
    return MemberGroupEntity.builder()
        .member(MemberEntity.builder().id(memberId).build())
        .challengeGroup(ChallengeGroupEntity.builder().id(challengeGroupId).build())
        .savedAmount(savedAmount)
        .build();
  }

  public MemberGroup updateSavedAmount(SavingMoneyRequestDto savingMoneyRequestDto) {
    savedAmount = savedAmount + savingMoneyRequestDto.getSavingAmount();
    return this;
  }
}
