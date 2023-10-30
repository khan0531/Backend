package com.cozybinarybase.accountstopthestore.model.category.service;

import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryDeleteResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public CategorySaveResponseDto saveCategory(CategorySaveRequestDto requestDto, Long memberId) {
    MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(
        () -> new RuntimeException("찾을 수 없는 회원 번호입니다.")
    );

    CategoryEntity categoryEntity = categoryRepository.save(CategoryEntity.builder()
        .name(requestDto.getCategoryName())
        .member(memberEntity)
        .build());

    return CategorySaveResponseDto.fromEntity(categoryEntity);
  }

  @Transactional
  public CategoryUpdateResponseDto updateCategory(
      Long categoryId,
      CategoryUpdateRequestDto requestDto,
      Long memberId
  ) {
    if (memberRepository.findById(memberId).isEmpty()) {
      throw new RuntimeException("찾을 수 없는 회원 번호입니다.");
    }

    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
        () -> new RuntimeException("찾을 수 없는 카테고리 번호입니다.")
    );

    categoryEntity.update(requestDto.getCategoryName());

    return CategoryUpdateResponseDto.fromEntity(categoryEntity);
  }

  @Transactional
  public CategoryDeleteResponseDto deleteCategory(Long categoryId, Long memberId) {
    if (memberRepository.findById(memberId).isEmpty()) {
      throw new RuntimeException("찾을 수 없는 회원 번호입니다.");
    }
    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
        () -> new RuntimeException("찾을 수 없는 카테고리 번호입니다.")
    );

    categoryRepository.delete(categoryEntity);

    return CategoryDeleteResponseDto.builder()
        .categoryId(categoryId)
        .build();
  }

  @Transactional(readOnly = true)
  public CategoryResponseDto getCategory(Long categoryId, Long memberId) {
    if (memberRepository.findById(memberId).isEmpty()) {
      throw new RuntimeException("찾을 수 없는 회원 번호입니다.");
    }
    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
        () -> new RuntimeException("찾을 수 없는 카테고리 번호입니다.")
    );

    return CategoryResponseDto.fromEntity(categoryEntity);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponseDto> allCategory(Long memberId) {
    if (memberRepository.findById(memberId).isEmpty()) {
      throw new RuntimeException("찾을 수 없는 회원 번호입니다.");
    }

    List<CategoryEntity> categoryEntityList = categoryRepository.findByMember_Id(memberId);

    return categoryEntityList.stream()
        .map(CategoryResponseDto::fromEntity)
        .collect(Collectors.toList());
  }
}
