package com.cozybinarybase.accountstopthestore.model.asset.service;

import com.cozybinarybase.accountstopthestore.model.asset.domain.Asset;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.exception.AssetNotValidException;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
      AssetSaveRequestDto requestDto, Member member
  ) {
    memberService.validateAndGetMember(member);
    existAssetOfMember(requestDto.getAssetName(), requestDto.getAssetType(), member.getId());

    AssetEntity assetEntity =
        assetRepository.save(asset.createAsset(requestDto, member.getId()).toEntity());

    return AssetSaveResponseDto.fromEntity(assetEntity);
  }

  @Transactional
  public AssetUpdateResponseDto updateAsset(
      Long assetId, AssetUpdateRequestDto requestDto, Member member
  ) {
    memberService.validateAndGetMember(member);

    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        AssetNotValidException::new
    );

    existAssetOfMember(requestDto.getAssetName(), requestDto.getAssetType(), member.getId());

    Asset assetDomain = Asset.fromEntity(assetEntity);
    assetDomain.update(requestDto);

    AssetEntity updateAssetEntity = assetDomain.toEntity();
    assetRepository.save(updateAssetEntity);

    return AssetUpdateResponseDto.fromEntity(updateAssetEntity);
  }

  @Transactional
  public void deleteAsset(Long assetId, Member member) {
    memberService.validateAndGetMember(member);

    AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(
        AssetNotValidException::new
    );

    assetRepository.delete(assetEntity);
  }

  @Transactional(readOnly = true)
  public List<AssetResponseDto> allAsset(Member member) {
    memberService.validateAndGetMember(member);

    List<AssetEntity> assetEntityList = assetRepository.findByMember_Id(member.getId());

    return assetEntityList.stream()
        .map(AssetResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  private void existAssetOfMember(String assetName, AssetType assetType, Long memberId) {
    if (assetRepository.existsByNameAndTypeAndMember_Id(assetName, assetType, memberId)) {
      throw new AssetNotValidException("이미 존재하는 자산입니다.");
    }
  }
}
