package com.cozybinarybase.accountstopthestore.model.accountbook.controller;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookImageResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.service.AccountBookService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/accounts")
@RestController
public class AccountBookController {

  private final AccountBookService accountBookService;

  @PostMapping
  public ResponseEntity<?> saveAccountBook(
      @RequestBody AccountBookSaveRequestDto requestDto,
      @AuthenticationPrincipal Member member
  ) {
    AccountBookSaveResponseDto responseDto =
        accountBookService.saveAccountBook(requestDto, member);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PutMapping("/{accountId}")
  public ResponseEntity<?> updateAccountBook(
      @PathVariable Long accountId,
      @RequestBody AccountBookUpdateRequestDto requestDto,
      @AuthenticationPrincipal Member member) {
    AccountBookUpdateResponseDto responseDto =
        accountBookService.updateAccountBook(accountId, requestDto, member);
    return ResponseEntity.ok().body(responseDto);
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<?> deleteAccountBook(
      @PathVariable Long accountId,
      @AuthenticationPrincipal Member member) {
    accountBookService.deleteAccountBook(accountId, member);
    return ResponseEntity.ok().body("가계 내역이 삭제되었습니다.");
  }

  @GetMapping("/{accountId}/images")
  public ResponseEntity<?> getAccountBookImages(
      @PathVariable Long accountId,
      @AuthenticationPrincipal Member member) {
    List<AccountBookImageResponseDto> accountBookImages = accountBookService.getAccountBookImages(
        accountId, member);
    return ResponseEntity.ok().body(accountBookImages);
  }
}
