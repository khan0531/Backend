package com.cozybinarybase.accountstopthestore.model.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeVerifyRequestDto {
  private String email;
  private String code;
}
