package com.cozybinarybase.accountstopthestore.model.asset.persist.repository;

import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

  boolean existsByNameAndTypeAndMember_Id(String name, AssetType type, Long memberId);

  List<AssetEntity> findByMember_Id(Long memberId);
}
