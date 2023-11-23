package com.cozybinarybase.accountstopthestore.model.category.service;

import com.cozybinarybase.accountstopthestore.model.category.domain.Category;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import com.cozybinarybase.accountstopthestore.model.category.exception.CategoryNotValidException;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final MemberService memberService;
  private final Category category;

  @Transactional
  public CategorySaveResponseDto saveCategory(
      CategorySaveRequestDto requestDto, Member member
  ) {
    memberService.validateAndGetMember(member);

    existCategoryOfMember(
        requestDto.getCategoryName(), requestDto.getCategoryType(), member.getId());

    CategoryEntity categoryEntity =
        categoryRepository.save(category.createCategory(requestDto, member.getId()).toEntity());
    return CategorySaveResponseDto.fromEntity(categoryEntity);
  }

  @Transactional
  public CategoryUpdateResponseDto updateCategory(
      Long categoryId,
      CategoryUpdateRequestDto requestDto,
      Member member
  ) {
    memberService.validateAndGetMember(member);

    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
        CategoryNotValidException::new
    );

    existCategoryOfMember(
        requestDto.getCategoryName(), requestDto.getCategoryType(), member.getId());

    Category categoryDomain = Category.fromEntity(categoryEntity);
    categoryDomain.updateCategory(requestDto);

    CategoryEntity updateCategoryEntity = categoryDomain.toEntity();
    categoryRepository.save(updateCategoryEntity);

    return CategoryUpdateResponseDto.fromEntity(updateCategoryEntity);
  }

  @Transactional
  public void deleteCategory(Long categoryId, Member member) {
    memberService.validateAndGetMember(member);

    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
        CategoryNotValidException::new
    );

    categoryRepository.delete(categoryEntity);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponseDto> allCategory(Member member) {
    memberService.validateAndGetMember(member);

    List<CategoryEntity> categoryEntityList = categoryRepository.findByMember_Id(member.getId());

    return categoryEntityList.stream()
        .map(CategoryResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  private void existCategoryOfMember(String categoryName, CategoryType categoryType,
      Long memberId) {
    if (categoryRepository.existsByNameAndTypeAndMember_Id(
        categoryName, categoryType, memberId)
    ) {
      throw new CategoryNotValidException("이미 존재하는 카테고리입니다.");
    }
  }
}
