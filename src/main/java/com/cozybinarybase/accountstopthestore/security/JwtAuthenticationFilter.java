package com.cozybinarybase.accountstopthestore.security;


import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;
  private final MemberRepository memberRepository;
  private final MemberService memberService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Optional<String> accessTokenOpt = this.tokenProvider.extractAccessToken(request)
        .filter(tokenProvider::validateToken);

    if (accessTokenOpt.isPresent()) {
      String accessToken = accessTokenOpt.get();
      Authentication auth = getAuthentication(accessToken);
      SecurityContextHolder.getContext().setAuthentication(auth);
      filterChain.doFilter(request, response);
    } else {
      String refreshToken = this.tokenProvider.extractRefreshToken(request)
          .filter(tokenProvider::validateToken)
          .orElse(null);

      if (refreshToken != null) {
        checkRefreshTokenAndReIssueAccessToken(response, refreshToken);

        String newAccessToken = this.tokenProvider.extractAccessToken(request).orElse(null);
        if (newAccessToken != null) {
          Authentication newAuth = getAuthentication(newAccessToken);
          SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        filterChain.doFilter(request, response);
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.memberService.loadUserByUsername(this.tokenProvider.getUsername(jwt));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
    memberRepository.findByRefreshToken(refreshToken)
        .ifPresent(memberEntity -> {
          Member member = Member.fromEntity(memberEntity);
          String reIssuedRefreshToken = reIssueRefreshToken(member);
          tokenProvider.sendAccessAndRefreshToken(response, tokenProvider.generateAccessToken(member),
              reIssuedRefreshToken);
        });
  }

  private String reIssueRefreshToken(Member member) {
    String reIssuedRefreshToken = this.tokenProvider.generateRefreshToken();
    member.updateRefreshToken(reIssuedRefreshToken);
    this.memberRepository.saveAndFlush(member.toEntity());
    return reIssuedRefreshToken;
  }
}