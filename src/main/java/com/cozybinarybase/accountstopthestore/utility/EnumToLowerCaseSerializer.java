package com.cozybinarybase.accountstopthestore.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class EnumToLowerCaseSerializer extends JsonSerializer<Enum<?>> {

  @Override
  public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    if (value != null) {
      gen.writeString(value.name().toLowerCase());
    }
  }
}
