package com.cozybinarybase.accountstopthestore.model.message.controller;

import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import com.cozybinarybase.accountstopthestore.model.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
