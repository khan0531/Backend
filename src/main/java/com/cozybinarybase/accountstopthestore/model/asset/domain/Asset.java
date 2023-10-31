package com.cozybinarybase.accountstopthestore.model.asset.domain;

import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomApiException;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Asset {

  private final AssetRepository assetRepository;

  public AssetEntity save(AssetSaveRequestDto requestDto, MemberEntity memberEntity) {

    return assetRepository.save(AssetEntity.builder()
        .type(requestDto.getAssetType())
        .name(requestDto.getAssetName())
        .amount(requestDto.getAmount())
        .memo(requestDto.getMemo())
        .member(memberEntity)
        .build());
  }

  public AssetEntity update(
      Long assetId, AssetUpdateRequestDto requestDto
  ) {
    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 자산 번호입니다.")
    );

    assetEntity.update(
        requestDto.getAssetType(),
        requestDto.getAssetName(),
        requestDto.getAmount(),
        requestDto.getMemo()
    );

    return assetEntity;
  }
}
