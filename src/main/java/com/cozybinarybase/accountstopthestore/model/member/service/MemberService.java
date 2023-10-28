package com.cozybinarybase.accountstopthestore.model.member.service;

import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberResponse;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignUpRequest;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.memberRepository.findByEmail(email)
        .map(Member::fromEntity)
        .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 아닙니다. -> " + email));
  }

  public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {

    this.memberRepository.findByEmail(memberSignUpRequest.getEmail()).ifPresent(member -> {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    });

    Member member = Member.fromSignUpDto(memberSignUpRequest);
    member.passwordEncode(this.passwordEncoder);
    MemberEntity memberEntity = this.memberRepository.save(member.toEntity());

    return MemberResponse.fromEntity(memberEntity);
  }
}
