package com.cozybinarybase.accountstopthestore.model.member.domain;

import com.cozybinarybase.accountstopthestore.BaseTimeEntity;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignUpRequest;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.AuthType;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity  implements UserDetails {

  private Long id;

  private AuthType authType;

  private String oauthId;

  private String refreshToken;

  private String name;

  private String email;

  private String password;

  private Authority role;

  private LocalDateTime registeredAt;

  private LocalDateTime updatedAt;

  private LocalDateTime withdrawalAt;

  public void authorizeUser() {
    this.role = Authority.USER;
  }

  // 비밀번호 암호화 메소드
  public void passwordEncode(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
  }

  public void updateRefreshToken(String updateRefreshToken) {
    this.refreshToken = updateRefreshToken;
  }

  public static Member fromSignUpDto (MemberSignUpRequest memberSignUpRequest) {
    return Member.builder()
        .authType(AuthType.EMAIL)
        .email(memberSignUpRequest.getEmail())
        .password(memberSignUpRequest.getPassword())
        .name(memberSignUpRequest.getName())
        .role(Authority.USER)
        .build();
  }

  public MemberEntity toEntity() {
    return MemberEntity.builder()
        .id(this.id)
        .authType(this.authType)
        .oauthId(this.oauthId)
        .refreshToken(this.refreshToken)
        .name(this.name)
        .email(this.email)
        .password(this.password)
        .role(this.role)
        .registeredAt(this.registeredAt)
        .updatedAt(this.updatedAt)
        .withdrawalAt(this.withdrawalAt)
        .build();
  }

  public static Member fromEntity(MemberEntity memberEntity) {
    return Member.builder()
        .id(memberEntity.getId())
        .authType(memberEntity.getAuthType())
        .oauthId(memberEntity.getOauthId())
        .refreshToken(memberEntity.getRefreshToken())
        .name(memberEntity.getName())
        .email(memberEntity.getEmail())
        .password(memberEntity.getPassword())
        .role(memberEntity.getRole())
        .registeredAt(memberEntity.getRegisteredAt())
        .updatedAt(memberEntity.getUpdatedAt())
        .withdrawalAt(memberEntity.getWithdrawalAt())
        .build();
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(this.role).stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
