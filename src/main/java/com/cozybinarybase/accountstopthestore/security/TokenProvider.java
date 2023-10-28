package com.cozybinarybase.accountstopthestore.security;


import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TokenProvider {

//  private static final String KEY_ROLE = "role";

  private final MemberService memberService;

  private final MemberRepository memberRepository;

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpirationPeriod;


  /**
   * AccessToken 생성 메소드
   */
  public String generateAccessToken(UserDetails userDetails) {
    // 다음 정보들을 포함한 claims 생성
    //      - username
    //      - role
    //      - 생성 시간
    //      - 만료 시간
    //      - signature
    Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
//    claims.put(KEY_ROLE, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority));

    Date now = new Date();
    Date expireDate = new Date(now.getTime() + accessTokenExpirationPeriod);

    // jwt 발급
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey) // 사용할 암호화 알고리즘, 비밀키
        .compact();
  }

  /**
   * RefreshToken 생성
   * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
   */
  public String generateRefreshToken() {
      Date now = new Date();
      Date expireDate = new Date(now.getTime() + refreshTokenExpirationPeriod);
      // jwt 발급
      return Jwts.builder()
          .setExpiration(expireDate)
          .signWith(SignatureAlgorithm.HS512, this.secretKey) // 사용할 암호화 알고리즘, 비밀키
          .compact();
    }

  @Transactional
  public Authentication getAuthentication(String jwt) {
//    Jws<Claims> claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(jwt);
//    List roles = claims.getBody().get("roles", List.class);
//    String role = String.valueOf(roles.get(0));
    UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);

      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * RefreshToken DB 저장(업데이트)
   */
  public void updateRefreshToken(String email, String refreshToken) {
    Member member = Member.fromEntity(this.memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다.")));
    member.updateRefreshToken(refreshToken);
    this.memberRepository.save(member.toEntity());
  }

}
