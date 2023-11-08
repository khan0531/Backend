package com.cozybinarybase.accountstopthestore.model.images.service;

import com.cozybinarybase.accountstopthestore.model.images.dto.ImageUploadResponseDto;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity.ImageType;
import com.cozybinarybase.accountstopthestore.model.images.persist.repository.ImageRepository;
import com.cozybinarybase.accountstopthestore.model.images.service.util.ImageUtil;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.service.MemberService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

  @Value("${azure.ocr.endpoint}")
  private String AZURE_OCR_ENDPOINT;
  @Value("${azure.ocr.key}")
  private String AZURE_OCR_KEY;

  private static final String imageDir = "src/main/resources/static/images";

  private final ImageUtil imageUtil;

  private static final int THUMBNAIL_WIDTH = 512;
  private static final int THUMBNAIL_HEIGHT = 512;

  private final String MODEL_ID = "prebuilt-receipt";
  private final ImageRepository imageRepository;
  private final MemberService memberService;


  public List<ImageUploadResponseDto> uploadFile(MultipartFile file, boolean isReceipt,
      Member member) throws IOException {

    MemberEntity memberEntity = memberService.validateAndGetMember(member);

    List<ImageEntity> storedImages = new ArrayList<>();
    List<ImageUploadResponseDto> responseDtos = new ArrayList<>();

    // 파일 확장자 추출
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());

    // 파일 이름을 위한 UUID 생성
    String uuid = UUID.randomUUID().toString();

    // 원본 파일 이름과 경로 설정, 저장
    String originalFileName = uuid + "." + extension;
    Path originalFilePath = imageUtil.storeFile(file.getBytes(), Paths.get(imageDir + "/originals", originalFileName));
    storedImages.add(saveImageEntity(originalFilePath, originalFileName, ImageType.ORIGINAL, "image/" + extension, member));

    // 압축 이미지 생성 및 저장
    byte[] compressedImage = imageUtil.compressImage(file.getBytes());
    String compressedFileName = "comp-" + uuid + "." + "jpg";
    Path compressedFilePath = imageUtil.storeFile(compressedImage, Paths.get(imageDir + "/compresses", compressedFileName));
    // MIME 타입은 항상 JPEG으로 설정
    storedImages.add(saveImageEntity(compressedFilePath, compressedFileName, ImageType.COMPRESSED, "image/jpeg", member));

    // 썸네일 이미지 생성 및 저장
    byte[] thumbnailImage = imageUtil.createThumbnail(file.getBytes(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    String thumbnailFileName = "thumb-" + uuid + "." + "jpg";
    Path thumbnailFilePath = imageUtil.storeFile(thumbnailImage, Paths.get(imageDir + "/thumbnails", thumbnailFileName));
    // MIME 타입은 항상 JPEG으로 설정
    storedImages.add(saveImageEntity(thumbnailFilePath, thumbnailFileName, ImageType.THUMBNAIL, "image/jpeg", member));

    // 이미지 엔티티 저장 후 응답 생성(미구현, 스트림으로)
    for (ImageEntity storedImage : storedImages) {
      ImageUploadResponseDto responseDto = new ImageUploadResponseDto();
      // 응답 생성 및 추가(미구현)
      responseDtos.add(responseDto);
    }

    // OCR 수행 로직(미구현)

    return responseDtos;
  }

  private ImageEntity saveImageEntity(Path filePath, String fileName, ImageType imageType, String mimeType,
      Member member) {
    return imageRepository.save(ImageEntity.builder()
        .imagePath(filePath.toString())
        .imageFileName(fileName)
        .imageType(imageType)
        .member(member.toEntity())
        .mimeType(mimeType)
        .uploadedAt(LocalDateTime.now())
        .build());
  }
}
