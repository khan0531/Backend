package com.cozybinarybase.accountstopthestore.model.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetDeleteResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.asset.persist.entity.AssetEntity;
import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssetTest {

  @InjectMocks
  private Asset asset;

  @Mock
  private AssetRepository assetRepository;

  @Spy
  private ObjectMapper objectMapper;

  @Test
  void 자산_추가_test() throws Exception {
    // given
    AssetSaveRequestDto assetSaveRequestDto = new AssetSaveRequestDto();

    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");

    // stub 1
    AssetEntity assetEntity = AssetEntity.builder()
        .id(1L)
        .type(AssetType.MONEY)
        .member(hong)
        .build();
    when(assetRepository.save(any())).thenReturn(assetEntity);

    // when
    AssetSaveResponseDto assetSaveResponseDto = AssetSaveResponseDto.fromEntity(
        asset.save(assetSaveRequestDto, hong)
    );

    String responseBody = objectMapper.writeValueAsString(assetSaveResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(assetSaveResponseDto.getAssetId()).isEqualTo(1L);
  }

  @Test
  void 자산_수정_test() throws Exception {
    // given
    AssetUpdateRequestDto assetUpdateRequestDto = new AssetUpdateRequestDto();
    assetUpdateRequestDto.setAssetType(AssetType.BANK);
    assetUpdateRequestDto.setAssetName("월급");

    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");

    // stub 1
    AssetEntity assetEntity = AssetEntity.builder()
        .id(1L)
        .type(AssetType.MONEY)
        .name("현금")
        .member(hong)
        .build();
    when(assetRepository.findById(any())).thenReturn(Optional.of(assetEntity));

    // when
    AssetUpdateResponseDto assetUpdateResponseDto = AssetUpdateResponseDto.fromEntity(
        asset.update(1L, assetUpdateRequestDto)
    );

    String responseBody = objectMapper.writeValueAsString(assetUpdateResponseDto);
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(assetUpdateResponseDto.getAssetName()).isEqualTo("월급");
  }

  @Test
  void 자산_삭제_test() throws Exception {
    // given
    MemberEntity hong = new MemberEntity();
    hong.setId(1L);
    hong.setName("홍길동");
    hong.setEmail("test@test.com");

    // stub 1
    AssetEntity assetEntity = AssetEntity.builder()
        .id(1L)
        .type(AssetType.MONEY)
        .name("현금")
        .member(hong)
        .build();
    when(assetRepository.findById(any())).thenReturn(Optional.of(assetEntity));

    // when
    Long assetId = asset.delete(1L);

    String responseBody = objectMapper.writeValueAsString(AssetDeleteResponseDto.builder()
        .assetId(assetId)
        .build()
    );
    System.out.println("테스트: " + responseBody);

    // then
    assertThat(assetId).isEqualTo(1L);
  }
}