package com.cozybinarybase.accountstopthestore.model.message.service;

import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import com.cozybinarybase.accountstopthestore.model.message.persist.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;
  private final SimpMessagingTemplate messagingTemplate;

  public void saveAndSend(Message message) {
    messageRepository.save(message.toEntity());
    messagingTemplate.convertAndSend("/chat/groups/" + message.getGroupId(), message);
  }
}
