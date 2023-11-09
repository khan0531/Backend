package com.cozybinarybase.accountstopthestore.model.message.dto.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
  ENTER("ENTER"), LEAVE("LEAVE"), TALK("TALK");

  private final String type;
}
ㅌ