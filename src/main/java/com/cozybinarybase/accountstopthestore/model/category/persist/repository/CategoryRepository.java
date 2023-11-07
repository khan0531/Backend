package com.cozybinarybase.accountstopthestore.model.category.persist.repository;

import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  boolean existsByNameAndTypeAndMember_Id(String name, CategoryType type, Long memberId);

  List<CategoryEntity> findByMember_Id(Long memberId);

}
