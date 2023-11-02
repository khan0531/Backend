package com.cozybinarybase.accountstopthestore.model.asset.dto;

import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AssetResponseDto {

  private Long assetId;
  private Long memberId;
  private String assetType;
  private String assetName;
  private Long amount;
  private String memo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static AssetResponseDto fromEntity(AssetEntity assetEntity) {
    return AssetResponseDto.builder()
        .assetId(assetEntity.getId())
        .memberId(assetEntity.getMember().getId())
        .assetType(assetEntity.getType().name())
        .assetName(assetEntity.getName())
        .amount(assetEntity.getAmount())
        .memo(assetEntity.getMemo())
        .createdAt(assetEntity.getCreatedAt())
        .updatedAt(assetEntity.getUpdatedAt())
        .build();
  }
}
