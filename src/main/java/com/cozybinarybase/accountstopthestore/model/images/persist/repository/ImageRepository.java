package com.cozybinarybase.accountstopthestore.model.images.persist.repository;

import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
  boolean existsByImageFileName(String imageFileName);
}
