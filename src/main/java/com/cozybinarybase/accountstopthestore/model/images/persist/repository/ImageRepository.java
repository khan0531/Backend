package com.cozybinarybase.accountstopthestore.model.images.persist.repository;

import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity.ImageType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
  Optional<ImageEntity> findByImageFileName(String imageFileName);

  Optional<ImageEntity> findByImageIdAndImageTypeAndMember_Id(Long imageId, ImageType imageType, Long memberId);

  Optional<ImageEntity> findByOriginalImage_ImageIdAndImageType(Long originalImageId, ImageType imageType);
}
