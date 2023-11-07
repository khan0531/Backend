package com.cozybinarybase.accountstopthestore.model.asset.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cozybinarybase.accountstopthestore.model.asset.domain.Asset;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.dto.constants.Authority;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

  @InjectMocks
  private AssetService assetService;

  @Mock
  private MemberService memberService;

  @Mock
  private AssetRepository assetRepository;

  @Mock
  private Asset assetMock;

  @Test
  void 자산_추가_test() throws Exception {
    // given
    AssetSaveRequestDto requestDto = new AssetSaveRequestDto();
    requestDto.setAssetType(AssetType.CARD);
    requestDto.setAssetName("우리카드");
    requestDto.setAmount(100000L);
    requestDto.setStatementDay(10);
    requestDto.setDueDay(20);
    requestDto.setMemo("메모");

    MemberEntity member = new MemberEntity();
    member.setId(1L);
    member.setRole(Authority.USER);
    member.setEmail("test@test.com");
    member.setPassword("1234");
    member.setName("홍길동");
    Member loginMember = Member.fromEntity(member);

    AssetEntity savedAsset = AssetEntity.builder()
        .id(1L)
        .type(AssetType.CARD)
        .name("우리카드")
        .amount(100000L)
        .statementDay(10)
        .dueDay(20)
        .memo("메모")
        .build();

    Asset assetDomain = Asset.builder()
        .type(AssetType.CARD)
        .name("우리카드")
        .amount(100000L)
        .statementDay(10)
        .dueDay(20)
        .memo("메모")
        .build();

    // stub 1
    when(memberService.validateAndGetMember(loginMember)).thenReturn(member);

    // stub 2
    when(assetRepository.existsByNameAndTypeAndMember_Id(any(), any(), any())).thenReturn(false);

    // stub 3
    when(assetMock.createAsset(requestDto, 1L)).thenReturn(assetDomain);

    // stub 4
    when(assetRepository.save(any())).thenReturn(savedAsset);

    // when
    AssetSaveResponseDto responseDto = assetService.saveAsset(requestDto, loginMember);

    // then
    assertEquals("카드", responseDto.getAssetType());
    assertEquals("우리카드", requestDto.getAssetName());
    assertEquals(100000L, requestDto.getAmount());
    assertEquals(10, requestDto.getStatementDay());
    assertEquals(20, requestDto.getDueDay());
    assertEquals("메모", requestDto.getMemo());
  }
}