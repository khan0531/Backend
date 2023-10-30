package com.cozybinarybase.accountstopthestore.model.category.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.persist.entity.CategoryEntity;
import com.cozybinarybase.accountstopthestore.model.category.persist.repository.CategoryRepository;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.AuthType;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    MemberEntity hong = MemberEntity.builder()
        .id(1L)
        .name("홍길동")
        .authType(AuthType.EMAIL)
        .email("hong@test.com")
        .password("12345678")
        .role(Authority.USER)
        .build();
    memberRepository.save(hong);

    CategoryEntity travel = CategoryEntity.builder()
        .id(1L)
        .name("travel")
        .member(hong)
        .build();
    categoryRepository.save(travel);

    entityManager.clear();
  }

  @WithUserDetails(value = "hong@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void 카테고리_생성_test() throws Exception {
    // given
    CategorySaveRequestDto requestDto = new CategorySaveRequestDto();
    requestDto.setCategoryName("shop");

    String requestBody = objectMapper.writeValueAsString(requestDto);
    System.out.println("테스트: " + requestBody);

    // when
    ResultActions resultActions = mockMvc.perform(post("/categories")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트: " + responseBody);

    // then
    resultActions.andExpect(status().isCreated());
  }

  @WithUserDetails(value = "hong@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void 카테고리_수정_test() throws Exception {
    // given
    CategoryUpdateRequestDto requestDto = new CategoryUpdateRequestDto();
    requestDto.setCategoryName("shop");

    String requestBody = objectMapper.writeValueAsString(requestDto);
    System.out.println("테스트: " + requestBody);

    // when
    ResultActions resultActions = mockMvc.perform(put("/categories/" + 1L)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트: " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
  }

  @WithUserDetails(value = "hong@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void 카테고리_삭제_test() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(delete("/categories/" + 1L)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트: " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
  }

  @WithUserDetails(value = "hong@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void 카테고리_조회_test() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(get("/categories/" + 2L)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트: " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
  }

  @WithUserDetails(value = "hong@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  void 카테고리_상세_조회_test() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(get("/categories")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트: " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
  }
}