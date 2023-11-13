package com.cozybinarybase.accountstopthestore.model.images.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageResponseDto {

  private byte[] content;
  private String mimeType;

}
