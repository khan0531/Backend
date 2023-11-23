package com.cozybinarybase.accountstopthestore.common.handler.type;

import com.cozybinarybase.accountstopthestore.utility.EnumToLowerCaseSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EnumToLowerCaseSerializer.class)
public enum StatusType {
  SUCCESS, ERROR, FAIL
}