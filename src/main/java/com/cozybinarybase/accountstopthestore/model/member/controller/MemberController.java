package com.cozybinarybase.accountstopthestore.model.member.controller;

import com.cozybinarybase.accountstopthestore.model.member.dto.MemberResponseDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignInRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.MemberSignUpRequestDto;
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

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequest) {
    MemberResponseDto result = this.memberService.signUp(memberSignUpRequest);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody @Valid MemberSignInRequestDto memberSignInRequestDto) {
    this.memberService.signIn(memberSignInRequestDto);
    return ResponseEntity.ok().build();
  }
}
