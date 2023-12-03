package com.cozybinarybase.accountstopthestore.model.category.persist.repository;

import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  boolean existsByNameAndTypeAndMember_Id(String name, CategoryType type, Long memberId);

  List<CategoryEntity> findByMember_Id(Long memberId);

  Optional<CategoryEntity> findByNameAndMember_Id(String categoryName, Long memberId);

  void deleteAllByMemberId(Long id);

  Collection<CategoryEntity> findByMemberIdAndNameStartingWithIgnoreCase(Long id, String query);
}
