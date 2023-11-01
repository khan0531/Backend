package com.cozybinarybase.accountstopthestore.model.asset.domain;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Asset {

  public AssetEntity createAsset(AssetSaveRequestDto requestDto, MemberEntity memberEntity) {

    return AssetEntity.builder()
        .type(requestDto.getAssetType())
        .name(requestDto.getAssetName())
        .amount(requestDto.getAmount())
        .memo(requestDto.getMemo())
        .member(memberEntity)
        .build();
  }

  public void update(AssetEntity assetEntity, AssetUpdateRequestDto requestDto) {
    Optional.ofNullable(requestDto.getAssetType()).ifPresent(assetEntity::setType);
    Optional.ofNullable(requestDto.getAssetName()).ifPresent(assetEntity::setName);
    Optional.ofNullable(requestDto.getAmount()).ifPresent(assetEntity::setAmount);
    Optional.ofNullable(requestDto.getMemo()).ifPresent(assetEntity::setMemo);
  }
}
