package com.cozybinarybase.accountstopthestore.model.category.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private MemberRepository memberRepository;

  @Spy
  private ObjectMapper objectMapper;

  @Test
  void 카테고리_등록_test() throws Exception {
    // given
    Long memberId = 1L;

    CategorySaveRequestDto categorySaveRequestDto = new CategorySaveRequestDto();
    categorySaveRequestDto.setCategoryName("여행");

    // stub 1
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");
    when(memberRepository.findById(any())).thenReturn(Optional.of(hong));

    // stub 2
    CategoryEntity categoryEntity = new CategoryEntity(1L, "여행", hong);
    when(categoryRepository.save(any())).thenReturn(categoryEntity);

    // when
    CategorySaveResponseDto categorySaveResponseDto = categoryService.saveCategory(
        categorySaveRequestDto, memberId);

    String responseBody = objectMapper.writeValueAsString(categorySaveResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(categorySaveResponseDto.getId()).isEqualTo(1L);
  }
}