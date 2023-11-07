package com.cozybinarybase.accountstopthestore.model.images.persist.entity;

import com.cozybinarybase.accountstopthestore.model.accountbook.persist.entity.AccountBookEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.time.LocalDateTime;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Image")
public class ImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "imageId", nullable = false, updatable = false)
  private Long imageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId", nullable = false)
  private MemberEntity member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accountId")
  private AccountBookEntity accountBook;

  @Column(name = "imageFileName", nullable = false)
  private String imageFileName;

  @Column(name = "imagePath", nullable = false)
  private String imagePath;

  @Column(name = "mimeType", nullable = false)
  private String mimeType;

  @Column(name = "uploadedAt", nullable = false)
  private LocalDateTime uploadedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "imageType", columnDefinition = "ENUM('original', 'compressed', 'thumbnail')", nullable = false)
  private ImageType imageType;

  public enum ImageType {
    ORIGINAL, COMPRESSED, THUMBNAIL
  }

}
