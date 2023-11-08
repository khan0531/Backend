package com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository;

import com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity.AccountBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

}
