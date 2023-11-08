package com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity.AccountBookEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

  Optional<AccountBookEntity> findByIdAndMember_Id(Long accountBookId, Long memberId);

  Page<AccountBookEntity> findByCreatedAtBetweenAndTransactionTypeAndMember_Id(
      LocalDateTime startDate, LocalDateTime endDate, TransactionType transactionType,
      Long memberId, Pageable pageable);
}
