package com.cozybinarybase.accountstopthestore.model.asset.dto;

import lombok.Getter;

@Getter
public class AssetWrapperResponseDto<T> {

  private final T asset;

  public AssetWrapperResponseDto(T assetData) {
    this.asset = assetData;
  }
}