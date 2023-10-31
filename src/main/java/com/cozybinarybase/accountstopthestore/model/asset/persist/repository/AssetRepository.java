package com.cozybinarybase.accountstopthestore.model.asset.persist.repository;

import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

}
