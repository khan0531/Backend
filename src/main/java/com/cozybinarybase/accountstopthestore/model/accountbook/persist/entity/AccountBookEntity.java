package com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity;

import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AccountBook")
public class AccountBookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "accountId", nullable = false, updatable = false)
  private Long accountId;

  @Column(name = "categoryId", nullable = false)
  private Long categoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId", nullable = false)
  private MemberEntity member;

  @Column(name = "assetId", nullable = false)
  private Long assetId;

  @Enumerated(EnumType.STRING)
  @Column(name = "transactionType", columnDefinition = "ENUM('수입', '지출')", nullable = false)
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

  @Column(name = "createdAt", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updatedAt", nullable = false)
  private LocalDateTime updatedAt;

  public enum TransactionType {
    수입, 지출
  }

  @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ImageEntity> images;

}
