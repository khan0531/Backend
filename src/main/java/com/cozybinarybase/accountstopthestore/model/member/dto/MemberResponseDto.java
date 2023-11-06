package com.cozybinarybase.accountstopthestore.model.member.dto;

import com.cozybinarybase.accountstopthestore.model.member.dto.constants.AuthType;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

  private AuthType authType;

  private String name;

  private String email;

  public static MemberResponseDto fromEntity(MemberEntity memberEntity) {
    return MemberResponseDto.builder()
        .authType(memberEntity.getAuthType())
        .name(memberEntity.getName())
        .email(memberEntity.getEmail())
        .build();
  }
}
