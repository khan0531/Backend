package com.cozybinarybase.accountstopthestore.model.member.controller;

import com.cozybinarybase.accountstopthestore.common.dto.MessageResponseDto;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailCodeRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailCodeVerifyRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignInRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignUpRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignUpResponseDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.PasswordChangeRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.ResetPasswordLinkRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.ResetPasswordRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.exception.VerificationCodeException;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/sign-up/email")
  public ResponseEntity<?> signUpWithEmail(@RequestBody @Valid EmailSignUpRequestDto memberSignUpRequest) {
    EmailSignUpResponseDto response = memberService.signUpWithEmail(memberSignUpRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/sign-in/email")
  public ResponseEntity<?> signInWithEmail(@RequestBody @Valid EmailSignInRequestDto emailSignInRequestDto) {
    memberService.signInWithEmail(emailSignInRequestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/withdrawal")
  public ResponseEntity<?> withdrawal(@AuthenticationPrincipal Member member) {
    MessageResponseDto response = memberService.withdrawal(member);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/password")
  public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeRequestDto requestDto,
      @AuthenticationPrincipal Member member) {
    MessageResponseDto response = memberService.changePassword(requestDto, member);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/email-verifications")
  public ResponseEntity<?> sendEmailVerificationCode(@RequestBody EmailCodeRequestDto requestDto) {
    MessageResponseDto response = memberService.sendEmailVerificationCode(requestDto.getEmail());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/email-verifications")
  public ResponseEntity<?> verifyEmail(@RequestBody EmailCodeVerifyRequestDto requestDto) {
    try {
      boolean isVerified = memberService.verifyEmail(requestDto);

      if (isVerified) {
        return ResponseEntity.ok().body("이메일 인증에 성공했습니다.");
      } else {
        return ResponseEntity.badRequest().body("올바른 코드가 아닙니다.");
      }
    } catch (VerificationCodeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> sendResetPasswordLink(@RequestBody ResetPasswordLinkRequestDto requestDto) {
    MessageResponseDto response = memberService.sendResetPasswordLink(requestDto.getEmail());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/reset-password/{memberId}/t/{token}")
  public ResponseEntity<?> resetPassword(
      @PathVariable Long memberId,
      @PathVariable String token,
      @RequestBody ResetPasswordRequestDto requestDto) {

    MessageResponseDto response = memberService.resetPassword(memberId, token, requestDto.getPassword());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/sign-out")
  public ResponseEntity<?> signOut() {
    ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
        .httpOnly(true)
        .path("/")
        .sameSite("None")
        .secure(true)
        .maxAge(0)
        .build();

    ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
        .httpOnly(true)
        .path("/")
        .sameSite("None")
        .secure(true)
        .maxAge(0)
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그아웃 되었습니다.");
  }
}
