package com.cozybinarybase.accountstopthestore.model.asset.controller;

import com.cozybinarybase.accountstopthestore.common.dto.ResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetSaveResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateRequestDto;
import com.cozybinarybase.accountstopthestore.model.asset.dto.AssetUpdateResponseDto;
import com.cozybinarybase.accountstopthestore.model.asset.service.AssetService;
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
@RequestMapping("/members")
@RestController
public class AssetController {

  private final AssetService assetService;

  @PostMapping("/{memberId}/assets")
  public ResponseEntity<?> saveAsset(
      @PathVariable Long memberId,
      @RequestBody @Valid AssetSaveRequestDto requestDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal Member member
  ) {
    AssetSaveResponseDto responseDto = assetService.saveAsset(memberId, requestDto, member);

    return new ResponseEntity<>(
        new ResponseDto<>(true, "자산 추가", responseDto), HttpStatus.CREATED
    );
  }

  @PutMapping("/{memberId}/assets/{assetId}")
  public ResponseEntity<?> updateAsset(
      @PathVariable Long memberId,
      @PathVariable Long assetId,
      @RequestBody @Valid AssetUpdateRequestDto requestDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal Member member
  ) {
    AssetUpdateResponseDto responseDto =
        assetService.updateAsset(memberId, assetId, requestDto, member);

    return new ResponseEntity<>(
        new ResponseDto<>(true, "자산 수정", responseDto), HttpStatus.OK
    );
  }
}
