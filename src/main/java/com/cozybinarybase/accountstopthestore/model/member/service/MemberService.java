package com.cozybinarybase.accountstopthestore.model.member.service;

import com.cozybinarybase.accountstopthestore.common.SimpleEmailService;
import com.cozybinarybase.accountstopthestore.common.dto.MessageResponseDto;
import com.cozybinarybase.accountstopthestore.common.handler.exception.MemberNotValidException;
import com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository.AccountBookRepository;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.images.persist.repository.ImageRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailCodeVerifyRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignInRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignUpRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.EmailSignUpResponseDto;
import com.cozybinarybase.accountstopthestore.model.member.dto.PasswordChangeRequestDto;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.VerificationCode;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.VerificationCodeRepository;
import com.cozybinarybase.accountstopthestore.model.member.service.util.MemberUtil;
import com.cozybinarybase.accountstopthestore.security.TokenProvider;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  private final AssetRepository assetRepository;
  private final AccountBookRepository accountBookRepository;
  private final CategoryRepository categoryRepository;
  private final ImageRepository imageRepository;
  private final VerificationCodeRepository verificationCodeRepository;

  private final SimpleEmailService simpleEmailService;
  private final MemberUtil memberUtil;
  private final StringRedisTemplate stringRedisTemplate;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.memberRepository.findByEmail(email)
        .map(Member::fromEntity)
        .map(member -> {
          if (member.getPassword() == null) {
            member.setPassword("123456789");
          }
          return member;
        })
        .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 아닙니다. -> " + email));
  }

  public EmailSignUpResponseDto signUpWithEmail(EmailSignUpRequestDto memberSignUpRequest) {

    this.memberRepository.findByEmail(memberSignUpRequest.getEmail()).ifPresent(member -> {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    });

    Member member = Member.fromSignUpDto(memberSignUpRequest);
    member.passwordEncode(this.passwordEncoder);
    MemberEntity memberEntity = this.memberRepository.save(member.toEntity());

    return EmailSignUpResponseDto.fromEntity(memberEntity);
  }

  public void signInWithEmail(EmailSignInRequestDto emailSignInRequestDto) {
    Member member = (Member) this.loadUserByUsername(emailSignInRequestDto.getEmail());
    if (!this.passwordEncoder.matches(emailSignInRequestDto.getPassword(), member.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getResponse();

    String accessToken = this.tokenProvider.generateAccessToken(member);
    String refreshToken = this.tokenProvider.generateRefreshToken();

    tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
  }

  public MemberEntity validateAndGetMember(Long memberId, Member member) {
    if (!Objects.equals(memberId, member.getId())) {
      throw new MemberNotValidException();
    }

    return memberRepository.findById(memberId).orElseThrow(
        MemberNotValidException::new
    );
  }

  public MemberEntity validateAndGetMember(Member member) {
    return memberRepository.findById(member.getId()).orElseThrow(
        MemberNotValidException::new
    );
  }

  public MessageResponseDto withdrawal(Member member) {
    MemberEntity memberEntity = this.validateAndGetMember(member);
    Long memberId = memberEntity.getId();
    imageRepository.deleteAllByMemberId(memberId);
    accountBookRepository.deleteAllByMemberId(memberId);
    assetRepository.deleteAllByMemberId(memberId);
    categoryRepository.deleteAllByMemberId(memberId);
    memberRepository.deleteById(memberId);
    return MessageResponseDto.builder()
        .message("회원 탈퇴가 완료되었습니다.")
        .build();
  }

  public MessageResponseDto changePassword(PasswordChangeRequestDto requestDto, Member member) {
    MemberEntity memberEntity = this.validateAndGetMember(member);
    memberEntity.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

    return MessageResponseDto.builder()
        .message("비밀번호가 변경되었습니다.")
        .build();
  }

  public MessageResponseDto sendEmailVerificationCode(String email) {
    String code = memberUtil.verificationCodeGenerator();

    simpleEmailService.sendEmail(email, "가게그만가계 가입 인증 코드입니다.", code);

    VerificationCode verificationCode = new VerificationCode();
    verificationCode.setEmail(email);
    verificationCode.setCode(code);
    verificationCode.setVerified(false);
    verificationCodeRepository.save(verificationCode);

    return MessageResponseDto.builder()
        .message("이메일 인증 메일을 전송했습니다.")
        .build();
  }

  public boolean verifyEmail(EmailCodeVerifyRequestDto requestDto) {
    VerificationCode verificationCode = verificationCodeRepository.findById(requestDto.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("인증 코드가 존재하지 않습니다."));

    if (verificationCode != null && verificationCode.getCode().equals(requestDto.getCode())) {
      verificationCode.setVerified(true);
      verificationCode.setTtl(TimeUnit.MINUTES.toSeconds(20));
      verificationCodeRepository.save(verificationCode); // 인증 상태 업데이트

      return true;
    }

    return false;
  }
}
