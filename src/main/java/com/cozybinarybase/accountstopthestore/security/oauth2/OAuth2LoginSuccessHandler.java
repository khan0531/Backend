package com.cozybinarybase.accountstopthestore.security.oauth2;

import com.cozybinarybase.accountstopthestore.security.TokenProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    String accessToken = this.tokenProvider.generateAccessToken(customOAuth2User);
    String refreshToken = this.tokenProvider.generateRefreshToken();
    this.tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
  }

//  private void loginSuccess(HttpServletResponse response, CustomOAuth2User customOAuth2User) throws IOException {
//    String accessToken = tokenProvider.generateAccessToken(customOAuth2User);
//    String refreshToken = tokenProvider.generateRefreshToken();
//
//    tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
//    tokenProvider.updateRefreshToken(customOAuth2User.getEmail(), refreshToken);
//  }
}
