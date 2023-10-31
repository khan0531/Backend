package com.cozybinarybase.accountstopthestore.model.asset.persist.repository;

import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

  Page<AssetEntity> findByType(AssetType assetType, Pageable pageable);
}
