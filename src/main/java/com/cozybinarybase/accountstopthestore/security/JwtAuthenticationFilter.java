package com.cozybinarybase.accountstopthestore.security;


import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  private final MemberRepository memberRepository;

  private final MemberService memberService;

  private static final String NO_CHECK_URL = "/sign-in";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().equals(NO_CHECK_URL)) {
      filterChain.doFilter(request, response); // "/sign-in" 요청이 들어오면, 다음 필터 호출
      return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
    }

    String refreshToken = this.tokenProvider.extractRefreshToken(request)
        .filter(tokenProvider::validateToken)
        .orElse(null);

    if (refreshToken != null) {
      checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
      return; // RefreshToken을 보낸 경우에는 AccessToken을 재발급 하고 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
    }


    if (refreshToken == null) {
      String accessToken = this.tokenProvider.extractAccessToken(request)
        .filter(tokenProvider::validateToken)
        .orElse(null);

      if (accessToken != null && tokenProvider.validateToken(accessToken)) {
        Authentication auth = getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

//        log.info(String.format("[%s] -> %s", this.tokenProvider.getUsername(accessToken), request.getRequestURI()));
      }

      filterChain.doFilter(request, response);
    }
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.memberService.loadUserByUsername(this.tokenProvider.getUsername(jwt));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }


  /**
   *  [리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급 메소드]
   *  파라미터로 들어온 헤더에서 추출한 리프레시 토큰으로 DB에서 유저를 찾고, 해당 유저가 있다면
   *  JwtService.createAccessToken()으로 AccessToken 생성,
   *  reIssueRefreshToken()로 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드 호출
   *  그 후 JwtService.sendAccessTokenAndRefreshToken()으로 응답 헤더에 보내기
   */
  public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
    memberRepository.findByRefreshToken(refreshToken)
        .ifPresent(memberEntity -> {
          Member member = Member.fromEntity(memberEntity);
          String reIssuedRefreshToken = reIssueRefreshToken(member);
          tokenProvider.sendAccessAndRefreshToken(response, tokenProvider.generateAccessToken(member),
              reIssuedRefreshToken);
        });
  }

  /**
   * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
   * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
   * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
   */
  private String reIssueRefreshToken(Member member) {
    String reIssuedRefreshToken = this.tokenProvider.generateRefreshToken();
    member.updateRefreshToken(reIssuedRefreshToken);
    this.memberRepository.saveAndFlush(member.toEntity());
    return reIssuedRefreshToken;
  }


}