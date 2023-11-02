package com.cozybinarybase.accountstopthestore.model.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AssetDeleteResponseDto {

  private Long assetId;
}
