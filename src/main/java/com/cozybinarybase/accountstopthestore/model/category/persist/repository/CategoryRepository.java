package com.cozybinarybase.accountstopthestore.model.category.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
