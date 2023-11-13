package com.cozybinarybase.accountstopthestore.model.message.controller;

import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import com.cozybinarybase.accountstopthestore.model.message.service.MessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

  private final MessageService messageService;

  @MessageMapping("/send")
  public void chat(Message message) {
    messageService.saveAndSend(message);
  }

  //메세지 조회
  @GetMapping("/group/{groupId}/messages")
  public ResponseEntity<?> getMessages(@PathVariable Long groupId, @AuthenticationPrincipal Member member) {
    List<Message> messages = messageService.getMessages(groupId, member);
    return ResponseEntity.ok(messages);
  }
}
