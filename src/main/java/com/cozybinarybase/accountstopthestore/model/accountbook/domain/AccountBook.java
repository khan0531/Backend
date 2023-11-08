package com.cozybinarybase.accountstopthestore.model.accountbook.domain;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity.AccountBookEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Component
public class AccountBook {

  private Long id;
  private TransactionType transactionType;
  private String transactionDetail;
  private LocalDateTime transactedAt;
  private Long amount;
  private String address;
  private String memo;
  private Boolean isInstallment;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long categoryId;
  private Long memberId;
  private Long assetId;
  private String categoryName;
  private String assetName;

  public AccountBook createAccountBook(AccountBookSaveRequestDto requestDto, Long categoryId,
      Long assetId, Long memberId, String categoryName, String assetName) {
    return AccountBook.builder()
        .transactionType(requestDto.getTransactionType())
        .transactionDetail(requestDto.getTransactionDetail())
        .transactedAt(requestDto.getTransactedAt())
        .amount(requestDto.getAmount())
        .memo(requestDto.getMemo())
        .isInstallment(requestDto.getIsInstallment())
        .categoryId(categoryId)
        .assetId(assetId)
        .memberId(memberId)
        .categoryName(categoryName)
        .assetName(assetName)
        .build();
  }

  public AccountBookEntity toEntity() {
    return AccountBookEntity.builder()
        .id(this.id)
        .transactionType(this.transactionType)
        .transactionDetail(this.transactionDetail)
        .transactedAt(this.transactedAt)
        .amount(this.amount)
        .address(this.address)
        .memo(this.memo)
        .isInstallment(this.isInstallment)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .category(CategoryEntity.builder().id(this.categoryId).name(this.categoryName).build())
        .member(MemberEntity.builder().id(this.memberId).build())
        .asset(AssetEntity.builder().id(this.assetId).name(this.assetName).build())
        .build();
  }

  public static AccountBook fromEntity(AccountBookEntity accountBookEntity) {
    return AccountBook.builder()
        .id(accountBookEntity.getId())
        .transactionType(accountBookEntity.getTransactionType())
        .transactionDetail(accountBookEntity.getTransactionDetail())
        .transactedAt(accountBookEntity.getTransactedAt())
        .amount(accountBookEntity.getAmount())
        .address(accountBookEntity.getAddress())
        .memo(accountBookEntity.getMemo())
        .isInstallment(accountBookEntity.getIsInstallment())
        .createdAt(accountBookEntity.getCreatedAt())
        .updatedAt(accountBookEntity.getUpdatedAt())
        .categoryId(accountBookEntity.getCategory().getId())
        .memberId(accountBookEntity.getMember().getId())
        .assetId(accountBookEntity.getAsset().getId())
        .categoryName(accountBookEntity.getCategory().getName())
        .assetName(accountBookEntity.getAsset().getName())
        .build();
  }
}
