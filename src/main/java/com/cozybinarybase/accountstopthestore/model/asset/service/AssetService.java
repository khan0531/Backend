package com.cozybinarybase.accountstopthestore.model.asset.service;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetDeleteResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSearchTypeListResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSearchTypeResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.handler.exception.AssetNotValidException;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import com.cozybinarybase.accountstopthestore.model.asset.domain.Asset;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AssetService {

  private final AssetRepository assetRepository;
  private final MemberService memberService;
  private final Asset asset;

  @Transactional
  public AssetSaveResponseDto saveAsset(
      Long memberId, AssetSaveRequestDto requestDto, Member member
  ) {
    memberService.validateAndGetMember(memberId, member);

    AssetEntity assetEntity =
        assetRepository.save(asset.createAsset(requestDto, memberId).toEntity());

    return AssetSaveResponseDto.fromEntity(assetEntity);
  }

  @Transactional
  public AssetUpdateResponseDto updateAsset(
      Long memberId, Long assetId, AssetUpdateRequestDto requestDto, Member member
  ) {
    memberService.validateAndGetMember(memberId, member);

    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        AssetNotValidException::new
    );

    Asset assetDomain = Asset.fromEntity(assetEntity);
    assetDomain.update(requestDto);

    AssetEntity updateAssetEntity = assetDomain.toEntity();
    assetRepository.save(updateAssetEntity);

    return AssetUpdateResponseDto.fromEntity(updateAssetEntity);
  }

  @Transactional
  public AssetDeleteResponseDto deleteAsset(Long memberId, Long assetId, Member member) {
    memberService.validateAndGetMember(memberId, member);

    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        AssetNotValidException::new
    );

    assetRepository.delete(assetEntity);

    return AssetDeleteResponseDto.builder()
        .assetId(assetId)
        .build();
  }

  @Transactional(readOnly = true)
  public AssetResponseDto getAsset(Long memberId, Long assetId, Member member) {
    memberService.validateAndGetMember(memberId, member);

    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        AssetNotValidException::new
    );

    return AssetResponseDto.fromEntity(assetEntity);
  }

  @Transactional(readOnly = true)
  public AssetSearchTypeListResponseDto searchAssetType(
      Long memberId, AssetType assetType, int page, int limit, Member member
  ) {
    memberService.validateAndGetMember(memberId, member);

    PageRequest pageRequest = PageRequest.of(page, limit);
    List<AssetEntity> assetEntityList =
        assetRepository.findByType(assetType, pageRequest).getContent();
    List<AssetSearchTypeResponseDto> assetSearchTypeResponseDtoList = assetEntityList.stream()
        .map(AssetSearchTypeResponseDto::fromEntity)
        .collect(Collectors.toList());

    return AssetSearchTypeListResponseDto.of(memberId, assetSearchTypeResponseDtoList);
  }
}
