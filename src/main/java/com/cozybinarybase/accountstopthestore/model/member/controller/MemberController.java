package com.cozybinarybase.accountstopthestore.model.member.controller;

import com.cozybinarybase.accountstopthestore.model.member.dto.MemberResponse;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignInRequest;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignUpRequest;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  // 회원 가입
  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest) {
    MemberResponse result = this.memberService.signUp(memberSignUpRequest);
    return ResponseEntity.ok(result);
  }

  // 자체 로그인
  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody @Valid MemberSignInRequest memberSignInRequest) {
    this.memberService.signIn(memberSignInRequest);
    return ResponseEntity.ok().build();
  }
}
