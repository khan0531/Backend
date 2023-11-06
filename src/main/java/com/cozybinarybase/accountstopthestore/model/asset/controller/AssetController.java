package com.cozybinarybase.accountstopthestore.model.asset.controller;

import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetDeleteResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSearchTypeListResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.constants.AssetType;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.asset.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@Tag(name = "Asset API", description = "자산 관련 API 정보를 담고 있습니다.")
@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class AssetController {

  private final AssetService assetService;

  @Operation(summary = "자산 추가", description = "유저가 자산을 추가할 때 사용되는 API")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{memberId}/assets")
  public ResponseEntity<AssetSaveResponseDto> saveAsset(
      @PathVariable Long memberId,
      @RequestBody @Valid AssetSaveRequestDto requestDto,
      @AuthenticationPrincipal Member member
  ) {
    AssetSaveResponseDto responseDto = assetService.saveAsset(memberId, requestDto, member);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @Operation(summary = "자산 수정", description = "유저가 자산을 수정할 때 사용되는 API")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{memberId}/assets/{assetId}")
  public ResponseEntity<AssetUpdateResponseDto> updateAsset(
      @PathVariable Long memberId,
      @PathVariable Long assetId,
      @RequestBody @Valid AssetUpdateRequestDto requestDto,
      @AuthenticationPrincipal Member member
  ) {
    AssetUpdateResponseDto responseDto =
        assetService.updateAsset(memberId, assetId, requestDto, member);

    return ResponseEntity.ok(responseDto);
  }

  @Operation(summary = "자산 삭제", description = "유저가 자산을 삭제할 때 사용되는 API")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{memberId}/assets/{assetId}")
  public ResponseEntity<AssetDeleteResponseDto> deleteAsset(
      @PathVariable Long memberId,
      @PathVariable Long assetId,
      @AuthenticationPrincipal Member member
  ) {
    AssetDeleteResponseDto responseDto =
        assetService.deleteAsset(memberId, assetId, member);

    return ResponseEntity.ok(responseDto);
  }

  @Operation(summary = "자산 상세 조회", description = "유저가 자산을 상세 조회할 때 사용되는 API")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{memberId}/assets/{assetId}")
  public ResponseEntity<AssetResponseDto> getAsset(
      @PathVariable Long memberId,
      @PathVariable Long assetId,
      @AuthenticationPrincipal Member member
  ) {
    AssetResponseDto responseDto =
        assetService.getAsset(memberId, assetId, member);

    return ResponseEntity.ok(responseDto);
  }

  @Operation(summary = "자산 조회", description = "유저가 자산을 조회할 때 사용되는 API")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{memberId}/assets")
  public ResponseEntity<AssetSearchTypeListResponseDto> searchAssetType(
      @PathVariable Long memberId,
      @RequestParam AssetType assetType,
      @RequestParam(defaultValue = "0") int page, // max값 설정
      @RequestParam(defaultValue = "10") int limit, // max값 설정
      @AuthenticationPrincipal Member member
  ) {
    AssetSearchTypeListResponseDto responseDto =
        assetService.searchAssetType(memberId, assetType, page, limit, member);
    // 서비스 로직에 자산 이름 유효성 검사하는 부분 빠져있습니다.

    return ResponseEntity.ok(responseDto);
  }
}
