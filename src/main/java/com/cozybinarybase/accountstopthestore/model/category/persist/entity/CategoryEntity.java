package com.cozybinarybase.accountstopthestore.model.category.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class CategoryEntity {

	@Id
	@Column(name = "categoryId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "categoryName")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member")
	private MemberEntity member;
}
