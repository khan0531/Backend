package com.cozybinarybase.accountstopthestore.model.asset.domain;

import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomApiException;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

  public Long delete(Long assetId) {
    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 자산 번호입니다.")
    );

    assetRepository.delete(assetEntity);

    return assetId;
  }

  public AssetEntity get(Long assetId) {

    return assetRepository.findById(assetId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 자산 번호입니다.")
    );
  }

  public List<AssetEntity> searchType(AssetType assetType, int page, int limit) {
    PageRequest pageRequest = PageRequest.of(page, limit);

    return assetRepository.findByType(assetType, pageRequest).getContent();
  }
}
