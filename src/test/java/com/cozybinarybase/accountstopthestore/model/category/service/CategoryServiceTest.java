package com.cozybinarybase.accountstopthestore.model.category.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cozybinarybase.accountstopthestore.model.category.domain.Category;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.constants.CategoryType;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private Category categoryMock;

  @Test
  void 카테고리_생성_test() throws Exception {
    // given
    CategorySaveRequestDto requestDto = new CategorySaveRequestDto();
    requestDto.setCategoryName("월급");
    requestDto.setCategoryType(CategoryType.INCOME);

    MemberEntity member = new MemberEntity();
    member.setId(1L);
    member.setRole(Authority.USER);
    member.setEmail("test@test.com");
    member.setPassword("1234");
    member.setName("홍길동");

    Member loginMember = Member.fromEntity(member);
    CategoryEntity savedCategory = new CategoryEntity();
    savedCategory.setId(1L);
    savedCategory.setName("월급");
    savedCategory.setType(CategoryType.INCOME);
    savedCategory.setMember(member);

    Category categoryDomain = Category.builder()
        .name(requestDto.getCategoryName())
        .type(requestDto.getCategoryType())
        .memberId(1L)
        .build();

    // stub 1
    when(memberService.validateAndGetMember(1L, loginMember)).thenReturn(member);

    // stub 2
    when(categoryRepository.existsByNameAndTypeAndMember_Id(any(), any(), any())).thenReturn(false);

    // stub 3
    when(categoryMock.createCategory(requestDto, 1L)).thenReturn(categoryDomain);

    // stub 4
    when(categoryRepository.save(any())).thenReturn(savedCategory);

    // when
    CategorySaveResponseDto responseDto = categoryService.saveCategory(1L, requestDto, loginMember);

    // then
    assertEquals("월급", responseDto.getCategoryName());
    assertEquals("수입", responseDto.getCategoryType());
  }
}
