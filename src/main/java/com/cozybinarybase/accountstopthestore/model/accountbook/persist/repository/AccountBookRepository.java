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

  Page<AccountBookEntity> findByMember_IdAndCategory_NameStartingWithIgnoreCase(
      Long memberId, String keyword, Pageable pageable);

  Page<AccountBookEntity> findByMemoContainingAndTransactedAtBetweenAndCategory_NameAndAmountBetweenAndMember_Id(
      String keyword, LocalDateTime startDate, LocalDateTime endDate, String categoryName,
      Long minPrice, Long maxPrice, Long memberId, Pageable pageable
  );

  void deleteAllByMemberId(Long id);
}
