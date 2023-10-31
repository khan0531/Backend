package com.cozybinarybase.accountstopthestore.model.asset.service;

import com.cozybinarybase.accountstopthestore.common.handler.exception.CustomApiException;
import com.cozybinarybase.accountstopthestore.model.asset.domain.Asset;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetDeleteResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AssetService {

  private final AssetRepository assetRepository;
  private final MemberRepository memberRepository;
  private final Asset asset;

  @Transactional
  public AssetSaveResponseDto saveAsset(
      Long memberId, AssetSaveRequestDto requestDto, Member member
  ) {
    if (!Objects.equals(memberId, member.getId())) {
      throw new CustomApiException("회원 정보가 일치하지 않습니다.");
    }

    MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 회원 번호입니다.")
    );

    AssetEntity assetEntity = asset.save(requestDto, memberEntity);

    return AssetSaveResponseDto.fromEntity(assetEntity);
  }

  @Transactional
  public AssetUpdateResponseDto updateAsset(
      Long memberId, Long assetId, AssetUpdateRequestDto requestDto, Member member
  ) {
    if (!Objects.equals(memberId, member.getId())) {
      throw new CustomApiException("회원 정보가 일치하지 않습니다.");
    }

    memberRepository.findById(memberId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 회원 번호입니다.")
    );

    AssetEntity assetEntity = asset.update(assetId, requestDto);

    return AssetUpdateResponseDto.fromEntity(assetEntity);
  }

  @Transactional
  public AssetDeleteResponseDto deleteAsset(Long memberId, Long assetId, Member member) {
    if (!Objects.equals(memberId, member.getId())) {
      throw new CustomApiException("회원 정보가 일치하지 않습니다.");
    }

    memberRepository.findById(memberId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 회원 번호입니다.")
    );

    Long id = asset.delete(assetId);

    return AssetDeleteResponseDto.builder()
        .assetId(id)
        .build();
  }

  @Transactional(readOnly = true)
  public AssetResponseDto getAsset(Long memberId, Long assetId, Member member) {

    if (!Objects.equals(memberId, member.getId())) {
      throw new CustomApiException("회원 정보가 일치하지 않습니다.");
    }

    memberRepository.findById(memberId).orElseThrow(
        () -> new CustomApiException("찾을 수 없는 회원 번호입니다.")
    );

    AssetEntity assetEntity = asset.get(assetId);

    return AssetResponseDto.fromEntity(assetEntity);
  }
}
