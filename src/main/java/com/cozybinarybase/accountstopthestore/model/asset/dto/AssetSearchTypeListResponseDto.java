package com.cozybinarybase.accountstopthestore.model.asset.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AssetSearchTypeListResponseDto {

  private Long memberId;
  private List<AssetSearchTypeResponseDto> assets;

  public static AssetSearchTypeListResponseDto of(
      Long memberId,
      List<AssetSearchTypeResponseDto> assets
  ) {
    return AssetSearchTypeListResponseDto.builder()
        .memberId(memberId)
        .assets(assets)
        .build();
  }
}
