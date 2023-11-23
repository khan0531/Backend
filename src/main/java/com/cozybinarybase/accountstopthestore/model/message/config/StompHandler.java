package com.cozybinarybase.accountstopthestore.model.message.config;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.repository.ChallengeGroupRepository;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.repository.MemberGroupRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.security.TokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

  private final ChallengeGroupRepository challengeGroupRepository;

  private final MemberRepository memberRepository;

  private final MemberGroupRepository memberGroupRepository;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String email = (String) accessor.getSessionAttributes().get("email");
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      if (email == null || email.trim().isEmpty()) {
        throw new AccessDeniedException("Access token이 유효하지 않습니다.");
      }
    } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
      String destination = accessor.getDestination();
      Long groupId = Long.parseLong(destination.substring(destination.lastIndexOf("/") + 1));
      ChallengeGroupEntity challengeGroupEntity = challengeGroupRepository.findById(groupId)
          .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
      MemberEntity memberEntity = memberRepository.findByEmail(email)
          .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
      if (!memberGroupRepository.existsByMemberAndChallengeGroup(memberEntity,
          challengeGroupEntity)) {
        throw new AccessDeniedException("그룹에 속해 있지 않습니다.");
      }
    }

    return message;
  }
}
