package com.cozybinarybase.accountstopthestore.model.challenge.persist.entity;

import com.cozybinarybase.accountstopthestore.BaseTimeEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeGroupEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private Long targetAmount;

  private Long maxMembers;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  @ManyToOne
  private MemberEntity admin;

//  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//  private List<MessageEntity> messageList = new ArrayList<>();
}
