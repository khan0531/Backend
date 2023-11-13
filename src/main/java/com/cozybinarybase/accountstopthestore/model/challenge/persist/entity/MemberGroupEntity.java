package com.cozybinarybase.accountstopthestore.model.challenge.persist.entity;

import com.cozybinarybase.accountstopthestore.BaseTimeEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "member_group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroupEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private MemberEntity member;

  @ManyToOne
  private ChallengeGroupEntity challengeGroup;

  private Long savedAmount;
}
