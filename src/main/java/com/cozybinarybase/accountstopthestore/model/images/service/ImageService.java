package com.cozybinarybase.accountstopthestore.model.images.service;

import com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository.AccountBookRepository;
import com.cozybinarybase.accountstopthestore.model.images.dto.ImageUploadResponseDto;
import com.cozybinarybase.accountstopthestore.model.images.exception.FileIsNotValidImageException;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity.ImageType;
import com.cozybinarybase.accountstopthestore.model.images.persist.repository.ImageRepository;
import com.cozybinarybase.accountstopthestore.model.images.service.util.ImageUtil;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

  @Value("${azure.ocr.endpoint}")
  private String AZURE_OCR_ENDPOINT;
  @Value("${azure.ocr.key}")
  private String AZURE_OCR_KEY;
  @Value("${app.domainUrl}")
  private String domainUrl;

  private final String homeDirectory = System.getProperty("user.home");
  private final Path imagesDirectory = Paths.get(homeDirectory, "asts-images");


  private final ImageUtil imageUtil;

  private static final int THUMBNAIL_WIDTH = 512;
  private static final int THUMBNAIL_HEIGHT = 512;

  private final String MODEL_ID = "prebuilt-receipt";
  private final ImageRepository imageRepository;
  private final MemberService memberService;
  private final AccountBookRepository accountBookRepository;


  public ImageUploadResponseDto uploadFile(MultipartFile file, boolean isReceipt,
      Member member) throws IOException {

    memberService.validateAndGetMember(member);

    // file이 이미지 mime type이 아닐 경우 예외 처리
    if (!file.getContentType().startsWith("image")) {
      throw new FileIsNotValidImageException();
    }

    // 파일 확장자 추출
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());

    // 파일 이름을 위한 UUID 생성
    String uuid = UUID.randomUUID().toString();

    // 원본 파일 이름과 경로 설정, 저장
    String originalFileName = uuid + "." + extension;
    Path originalFilePath = imageUtil.storeFile(file.getBytes(),
        imagesDirectory.resolve("originals").resolve(originalFileName));
    ImageEntity originalImage = saveImageEntity(originalFilePath, originalFileName,
        ImageType.ORIGINAL, "image/" + extension, member, null);

    // 압축 이미지 생성 및 저장
    byte[] compressedImage = imageUtil.compressImage(file.getBytes());
    String compressedFileName = "comp-" + uuid + "." + "jpg";
    Path compressedFilePath = imageUtil.storeFile(compressedImage,
        imagesDirectory.resolve("compresses").resolve(compressedFileName));
    // 원본 이미지의 id를 참조
    saveImageEntity(compressedFilePath, compressedFileName, ImageType.COMPRESSED, "image/jpeg",
        member, originalImage);

    // 썸네일 이미지 생성 및 저장
    byte[] thumbnailImage = imageUtil.createThumbnail(file.getBytes(), THUMBNAIL_WIDTH,
        THUMBNAIL_HEIGHT);
    String thumbnailFileName = "thumb-" + uuid + "." + "jpg";
    Path thumbnailFilePath = imageUtil.storeFile(thumbnailImage,
        imagesDirectory.resolve("thumbnails").resolve(thumbnailFileName));
    // 원본 이미지의 id를 참조

    saveImageEntity(thumbnailFilePath, thumbnailFileName, ImageType.THUMBNAIL, "image/jpeg",
        member, originalImage);

    // OCR 수행 로직(미구현)

    // 원본 이미지의 id를 반환
    return ImageUploadResponseDto.builder()
        .imageId(originalImage.getImageId())
        .build();
  }

  private ImageEntity saveImageEntity(Path filePath, String fileName, ImageType imageType,
      String mimeType,
      Member member, ImageEntity originalImage) {
    return imageRepository.save(ImageEntity.builder()
        .imagePath(filePath.toString())
        .imageFileName(fileName)
        .imageType(imageType)
        .member(member.toEntity())
        .originalImage(originalImage)
        .mimeType(mimeType)
        .uploadedAt(LocalDateTime.now())
        .build());
  }

  public Resource loadImageAsResource(String imageFileName, Member member) {
    ImageEntity image = imageRepository.findByImageFileName(imageFileName)
        .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다."));

    memberService.validateAndGetMember(image.getMember().getId(), member);

    String imagePath = image.getImagePath();
    Resource resource = new FileSystemResource(imagePath);
    if (resource.exists() || resource.isReadable()) {
      return resource;
    } else {
      throw new RuntimeException("이미지를 읽을 수 없습니다.");
    }
  }
}
