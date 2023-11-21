package com.cozybinarybase.accountstopthestore.model.budget.service;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.budget.dto.BudgetResponseDto;
import com.cozybinarybase.accountstopthestore.model.budget.dto.BudgetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.budget.persist.entity.BudgetEntity;
import com.cozybinarybase.accountstopthestore.model.budget.persist.repository.BudgetRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.time.YearMonth;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {
  private final BudgetRepository budgetRepository;
  private final MemberService memberService;

  public Optional<BudgetEntity> getBudgetByYearMonth(YearMonth yearMonth) {
    return budgetRepository.findByYearMonth(yearMonth);
  }

  public BudgetResponseDto saveBudget(BudgetSaveRequestDto requestDto, Member member) {
    MemberEntity memberEntity = memberService.validateAndGetMember(member);

    BudgetEntity budgetEntity =
        budgetRepository.save(
            BudgetEntity.builder()
                .yearMonth(requestDto.getYearMonth().atEndOfMonth())
                .budget(requestDto.getBudget())
                .member(memberEntity)
                .build());

    return BudgetResponseDto.fromEntity(budgetEntity);
  }
}
