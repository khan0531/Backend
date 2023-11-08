package com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "AccountBook")
public class AccountBookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "accountId", nullable = false, updatable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "transactionType", nullable = false)
  private TransactionType transactionType;

  @Column(name = "transactionDetail", nullable = false)
  private String transactionDetail;

  @Column(name = "transactedAt", nullable = false)
  private LocalDateTime transactedAt;

  @Column(name = "amount", nullable = false)
  private Long amount;

  @Column(name = "address", columnDefinition = "TEXT")
  private String address;

  @Column(name = "memo", columnDefinition = "TEXT")
  private String memo;

  @Column(name = "isInstallment", nullable = false)
  private Boolean isInstallment;

  @CreatedDate
  @Column(name = "createdAt", nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updatedAt", nullable = false)
  private LocalDateTime updatedAt;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoryId")
  private CategoryEntity category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId")
  private MemberEntity member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assetId")
  private AssetEntity asset;

  @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ImageEntity> images;
}
