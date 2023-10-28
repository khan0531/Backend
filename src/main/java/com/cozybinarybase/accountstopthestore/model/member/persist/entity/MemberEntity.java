package com.cozybinarybase.accountstopthestore.model.member.persist.entity;


import com.cozybinarybase.accountstopthestore.model.member.dto.constants.AuthType;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private AuthType authType;

  private String oauthId; // 일반 회원 가입은 null

  private String refreshToken;

  private String name;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private Authority role;

  private LocalDateTime registeredAt;

  private LocalDateTime updatedAt;

  private LocalDateTime withdrawalAt;
}