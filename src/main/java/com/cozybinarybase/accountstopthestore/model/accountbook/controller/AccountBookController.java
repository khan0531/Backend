package com.cozybinarybase.accountstopthestore.model.accountbook.controller;

import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookImageResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.AccountBookUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.AccountBookCategoryResponseDto;
import com.cozybinarybase.accountstopthestore.model.accountbook.dto.constants.TransactionType;
import com.cozybinarybase.accountstopthestore.model.accountbook.service.AccountBookService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/accounts")
@RestController
public class AccountBookController {

  private final AccountBookService accountBookService;

  @GetMapping("/{accountId}")
  public ResponseEntity<?> getAccountBook(
      @PathVariable Long accountId,
      @AuthenticationPrincipal Member member) {
    AccountBookResponseDto responseDto = accountBookService.getAccountBook(accountId, member);
    return ResponseEntity.ok().body(responseDto);
  }

  @PostMapping
  public ResponseEntity<?> saveAccountBook(
      @RequestBody @Valid AccountBookSaveRequestDto requestDto,
      @AuthenticationPrincipal Member member
  ) {
    AccountBookSaveResponseDto responseDto =
        accountBookService.saveAccountBook(requestDto, member);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PutMapping("/{accountId}")
  public ResponseEntity<?> updateAccountBook(
      @PathVariable Long accountId,
      @RequestBody @Valid AccountBookUpdateRequestDto requestDto,
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

  @GetMapping
  public ResponseEntity<?> getAccountBooks(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam TransactionType transactionType,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int limit,
      @AuthenticationPrincipal Member member) {
    List<AccountBookResponseDto> accountBookResponseDtoList =
        accountBookService.getAccountBooks(startDate, endDate, transactionType, page, limit,
            member);

    return ResponseEntity.ok().body(accountBookResponseDtoList);
  }

  @GetMapping("/autocomplete")
  public ResponseEntity<?> autocomplete(
      @RequestParam String query,
      @RequestParam(defaultValue = "5") int limit,
      @AuthenticationPrincipal Member member) {
    AccountBookCategoryResponseDto names = accountBookService.getCategoryNamesByKeyword(
        query, limit, member);

    return ResponseEntity.ok().body(names);
  }

  @GetMapping("/search")
  public ResponseEntity<?> search(
      @RequestParam String keyword,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam String categoryName,
      @RequestParam Long minPrice,
      @RequestParam Long maxPrice,
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "10", required = false) int limit,
      @AuthenticationPrincipal Member member) {
    List<AccountBookResponseDto> responseDtos = accountBookService.search(
        keyword, startDate, endDate, categoryName, minPrice, maxPrice, page, limit, member);

    return ResponseEntity.ok().body(responseDtos);
  }

  @GetMapping("/{accountId}/images")
  public ResponseEntity<?> getAccountBookImages(
      @PathVariable Long accountId,
      @AuthenticationPrincipal Member member) {
    List<AccountBookImageResponseDto> accountBookImages = accountBookService.getAccountBookImages(
        accountId, member);
    return ResponseEntity.ok().body(accountBookImages);
  }

  @GetMapping("/statistics")
  public ResponseEntity<?> getTransactionStatistics(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam TransactionType transactionType,
      @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok().body(accountBookService.getTransactionStatistics(
        startDate, endDate, transactionType, member));
  }
}
