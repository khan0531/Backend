package com.cozybinarybase.accountstopthestore.model.asset.dto;

import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
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
public class AssetSearchTypeResponseDto {

  private Long assetId;
  private AssetType assetType;
  private String assetName;
  private Long amount;
  private String memo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static AssetSearchTypeResponseDto fromEntity(AssetEntity assetEntity) {
    return AssetSearchTypeResponseDto.builder()
        .assetId(assetEntity.getId())
        .assetType(assetEntity.getType())
        .assetName(assetEntity.getName())
        .amount(assetEntity.getAmount())
        .memo(assetEntity.getMemo())
        .createdAt(assetEntity.getCreatedAt())
        .updatedAt(assetEntity.getUpdatedAt())
        .build();
  }
}
