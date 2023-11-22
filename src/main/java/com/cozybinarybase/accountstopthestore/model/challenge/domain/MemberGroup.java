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
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MemberGroup {

  private Long id;
  private Long memberId;
  private String memberName;
  private Long challengeGroupId;
  private String groupName;
  private Long savedAmount;

  public static MemberGroup fromEntity(MemberGroupEntity memberGroupEntity) {
    return MemberGroup.builder()
        .id(memberGroupEntity.getId())
        .memberId(memberGroupEntity.getMember().getId())
        .memberName(memberGroupEntity.getMember().getName())
        .challengeGroupId(memberGroupEntity.getChallengeGroup().getId())
        .groupName(memberGroupEntity.getChallengeGroup().getName())
        .savedAmount(memberGroupEntity.getSavedAmount())
        .build();
  }

  public static MemberGroup create(ChallengeGroup group, Member member) {
    return MemberGroup.builder()
        .memberId(member.getId())
        .memberName(member.getName())
        .challengeGroupId(group.getId())
        .groupName(group.getName())
        .savedAmount(0L)
        .build();
  }

  public MemberGroupEntity toEntity() {
    return MemberGroupEntity.builder()
        .id(id)
        .member(MemberEntity.builder().id(memberId).name(memberName).build())
        .challengeGroup(ChallengeGroupEntity.builder().id(challengeGroupId).name(groupName).build())
        .savedAmount(savedAmount)
        .build();
  }

  public MemberGroup updateSavedAmount(SavingMoneyRequestDto savingMoneyRequestDto) {
    savedAmount = savedAmount + savingMoneyRequestDto.getSavingAmount();
    return this;
  }
}
