package com.cozybinarybase.accountstopthestore.model.category.controller;

import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategorySaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.category.dto.CategoryUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.category.service.CategoryService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<?> saveCategory(
      @RequestBody @Valid CategorySaveRequestDto requestDto,
      @AuthenticationPrincipal Member member,
      BindingResult bindingResult
  ) {
    CategorySaveResponseDto responseDto = categoryService.saveCategory(requestDto, member.getId());
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<?> updateCategory(
      @PathVariable Long categoryId,
      @RequestBody @Valid CategoryUpdateRequestDto requestDto,
      @AuthenticationPrincipal Member member,
      BindingResult bindingResult
  ) {
    CategoryUpdateResponseDto responseDto =
        categoryService.updateCategory(categoryId, requestDto, member.getId());
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}
