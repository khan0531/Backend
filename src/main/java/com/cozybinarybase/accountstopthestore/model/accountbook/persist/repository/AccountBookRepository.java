package com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository;

import com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity.AccountBookEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

  Optional<AccountBookEntity> findByIdAndMember_Id(Long accountBookId, Long memberId);
}
