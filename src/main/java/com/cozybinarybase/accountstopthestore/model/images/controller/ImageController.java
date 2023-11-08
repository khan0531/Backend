package com.cozybinarybase.accountstopthestore.model.images.controller;

import com.cozybinarybase.accountstopthestore.model.images.dto.ImageUploadResponseDto;
import com.cozybinarybase.accountstopthestore.model.images.service.ImageService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;
  private final ObjectMapper objectMapper;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ImageUploadResponseDto> imageUpload(
      @RequestPart("image-file") MultipartFile file,
      @RequestPart("json-data") String jsonData,
      @AuthenticationPrincipal Member member
  ) throws Exception {

    JsonNode rootNode = objectMapper.readTree(jsonData);
    boolean isReceipt = rootNode.get("isReceipt").asBoolean();

    // 파일 업로드 및 필요시 OCR 수행
    ImageUploadResponseDto responseMessage = imageService.uploadFile(file, isReceipt, member);

    return ResponseEntity.ok().body(responseMessage);
  }

  @GetMapping("/{imageFileName:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String imageFileName,
      @AuthenticationPrincipal Member member) {

      Resource resource = imageService.loadImageAsResource(imageFileName, member);
      String contentType = "image/jpeg";

      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .body(resource);
  }
}
