package com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.converter;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTransactionTypeConverter implements Converter<String, TransactionType> {
  @Override
  public TransactionType convert(String source) {
    for (TransactionType transactionType : TransactionType.values()) {
      if (transactionType.toValue().equals(source)) {
        return transactionType;
      }
    }
    throw new IllegalArgumentException("존재하지 않는 Enum 입니다: " + source);
  }
}
