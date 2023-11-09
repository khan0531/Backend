package com.cozybinarybase.accountstopthestore.model.message;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.repository.ChallengeGroupRepository;
import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper mapper;

  private final ChallengeGroupRepository challengeGroupRepository;

  private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

  // 소켓 연결 확인
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String path = session.getUri().getPath();
    Long groupId = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));

    // TODO : 현재 방을 보고 있는 맴버 추가
//    MemberGroup memberGroup = memberGroupRepository.findByGroupId(groupId);
    chatRoomSessionMap.computeIfAbsent(groupId, k -> new HashSet<>()).add(session);

    log.info("{} 연결됨", session.getId());
  }

  // 소켓 통신 시 메세지의 전송을 다루는 부분
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload {}", payload);

    // 페이로드 -> chatMessageDto로 변환
    Message messageDto = mapper.readValue(payload, Message.class);
    log.info("session {}", messageDto.toString());

    Long chatRoomId = messageDto.getGroupId();
    Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

    sendMessageToChatRoom(messageDto, chatRoomSession);

  }

  // 소켓 종료 확인
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    // TODO Auto-generated method stub
    log.info("{} 연결 끊김", session.getId());
  }

  private void sendMessageToChatRoom(Message message, Set<WebSocketSession> chatRoomSession) {
    chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, message));
  }

  public <T> void sendMessage(WebSocketSession session, T message) {
    try {
      session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}