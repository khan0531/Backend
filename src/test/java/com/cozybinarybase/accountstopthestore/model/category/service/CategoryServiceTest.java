package com.cozybinarybase.accountstopthestore.model.category.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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

  @Test
  void 카테고리_수정_test() throws Exception {
    // given
    Long memberId = 1L;

    CategoryUpdateRequestDto categoryUpdateRequestDto = new CategoryUpdateRequestDto();
    categoryUpdateRequestDto.setCategoryName("쇼핑");

    // stub 1
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");
    when(memberRepository.findById(any())).thenReturn(Optional.of(hong));

    // stub 2
    CategoryEntity existingCategory = new CategoryEntity(1L, "여행", hong);
    when(categoryRepository.findById(any())).thenReturn(Optional.of(existingCategory));

    // when
    CategoryUpdateResponseDto categoryUpdateResponseDto =
        categoryService.updateCategory(1L, categoryUpdateRequestDto, memberId);

    String responseBody = objectMapper.writeValueAsString(categoryUpdateResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(categoryUpdateResponseDto.getCategoryName()).isEqualTo("쇼핑");
  }

  @Test
  void 카테고리_삭제_test() throws Exception {
    // given
    Long memberId = 1L;

    // stub 1
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");
    when(memberRepository.findById(any())).thenReturn(Optional.of(hong));

    // stub 2
    CategoryEntity existingCategory = new CategoryEntity(1L, "여행", hong);
    when(categoryRepository.findById(any())).thenReturn(Optional.of(existingCategory));

    // when
    CategoryDeleteResponseDto categoryDeleteResponseDto =
        categoryService.deleteCategory(1L, memberId);

    String responseBody = objectMapper.writeValueAsString(categoryDeleteResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(categoryDeleteResponseDto.getCategoryId()).isEqualTo(1L);
  }

  @Test
  void 카테고리_상세_조회_test() throws Exception {
    // given
    Long memberId = 1L;

    // stub 1
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");
    when(memberRepository.findById(any())).thenReturn(Optional.of(hong));

    // stub 2
    CategoryEntity existingCategory = new CategoryEntity(1L, "여행", hong);
    when(categoryRepository.findById(any())).thenReturn(Optional.of(existingCategory));

    // when
    CategoryResponseDto categoryResponseDto =
        categoryService.getCategory(1L, memberId);

    String responseBody = objectMapper.writeValueAsString(categoryResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(categoryResponseDto.getCategoryName()).isEqualTo("여행");
    assertThat(categoryResponseDto.getCategoryOwnerId()).isEqualTo(1L);
  }

  @Test
  void 카테고리_리스트_조회_test() throws Exception {
    // given
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");

    CategoryEntity categoryEntity1 = new CategoryEntity(1L, "여행", hong);
    CategoryEntity categoryEntity2 = new CategoryEntity(2L, "쇼핑", hong);

    List<CategoryEntity> categoryEntityList = new ArrayList<>();
    categoryEntityList.add(categoryEntity1);
    categoryEntityList.add(categoryEntity2);

    // stub 1
    when(memberRepository.findById(any())).thenReturn(Optional.of(hong));

    // stub 2
    when(categoryRepository.findByMember_Id(any())).thenReturn(categoryEntityList);

    // when
    List<CategoryResponseDto> categoryResponseDtoList = categoryService.allCategory(1L);

    String responseBody = objectMapper.writeValueAsString(categoryResponseDtoList);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(categoryResponseDtoList.size()).isEqualTo(2);
  }
}