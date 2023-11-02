package com.cozybinarybase.accountstopthestore.model.asset.domain;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Asset {

  private Long id;
  private AssetType type;
  private String name;
  private Long amount;
  private String memo;
  private Long memberId;

  public Asset createAsset(AssetSaveRequestDto requestDto, Long memberId) {

    return Asset.builder()
        .type(requestDto.getAssetType())
        .name(requestDto.getAssetName())
        .amount(requestDto.getAmount())
        .memo(requestDto.getMemo())
        .memberId(memberId)
        .build();
  }

  public void update(AssetUpdateRequestDto requestDto) {
    if (requestDto.getAssetType() != null) {
      this.type = requestDto.getAssetType();
    }
    if (requestDto.getAssetName() != null) {
      this.name = requestDto.getAssetName();
    }
    if (requestDto.getAmount() != null) {
      this.amount = requestDto.getAmount();
    }
    if (requestDto.getMemo() != null) {
      this.memo = requestDto.getMemo();
    }
  }

  public AssetEntity toEntity() {
    return AssetEntity.builder()
        .type(this.type)
        .name(this.name)
        .amount(this.amount)
        .memo(this.memo)
        .member(MemberEntity.builder().id(this.memberId).build())
        .build();
  }
}
