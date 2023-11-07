package com.cozybinarybase.accountstopthestore.model.images.service;

import com.cozybinarybase.accountstopthestore.model.images.dto.ImageUploadResponseDto;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity;
import com.cozybinarybase.accountstopthestore.model.images.persist.entity.ImageEntity.ImageType;
import com.cozybinarybase.accountstopthestore.model.images.persist.repository.ImageRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
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

  private static final String imageDir = "static/images";

  private static final int THUMBNAIL_WIDTH = 256;
  private static final int THUMBNAIL_HEIGHT = 256;

  private final String MODEL_ID = "prebuilt-receipt";
  private final ImageRepository imageRepository;

  public List<ImageUploadResponseDto> uploadFile(MultipartFile file, boolean isReceipt,
      Member member) throws IOException {
    List<ImageEntity> storedImages = new ArrayList<>();
    List<ImageUploadResponseDto> responseDtos = new ArrayList<>();

    // 파일 확장자 추출
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());

    // 파일 이름을 위한 UUID 생성
    String uuid = UUID.randomUUID().toString();

    // 원본 파일 이름과 경로 설정, 저장
    String originalFileName = uuid + "." + extension;
    Path originalFilePath = storeFile(file.getBytes(), Paths.get(imageDir + "/originals", originalFileName));
    storedImages.add(saveImageEntity(originalFilePath, ImageType.ORIGINAL, "image/" + extension, member));

    // 압축 이미지 생성 및 저장
    byte[] compressedImage = compressImage(file.getBytes());
    String compressedFileName = "comp-" + originalFileName;
    Path compressedFilePath = storeFile(compressedImage, Paths.get(imageDir + "compresses", compressedFileName));
    // MIME 타입은 항상 JPEG으로 설정
    storedImages.add(saveImageEntity(compressedFilePath, ImageType.COMPRESSED, "image/jpeg", member));

    // 썸네일 이미지 생성 및 저장
    byte[] thumbnailImage = createThumbnail(file.getBytes(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    String thumbnailFileName = "thumb-" + originalFileName;
    Path thumbnailFilePath = storeFile(thumbnailImage, Paths.get(imageDir + "thumbnails", thumbnailFileName));
    // MIME 타입은 항상 JPEG으로 설정
    storedImages.add(saveImageEntity(thumbnailFilePath, ImageType.THUMBNAIL, "image/jpeg", member));

    // 이미지 엔티티 저장 후 응답 생성(미구현)
    for (ImageEntity storedImage : storedImages) {
      ImageUploadResponseDto responseDto = new ImageUploadResponseDto();
      // 응답 생성 및 추가(미구현)
      responseDtos.add(responseDto);
    }

    // OCR 수행 로직(미구현)

    return responseDtos;
  }

  private Path storeFile(byte[] fileData, Path path) throws IOException {
    Files.write(path, fileData);
    return path;
  }

  private ImageEntity saveImageEntity(Path filePath, ImageType imageType, String mimeType,
      Member member) {
    return imageRepository.save(ImageEntity.builder()
        .imagePath(filePath.toString())
        .imageType(imageType)
        .member(member.toEntity())
        .mimeType(mimeType)
        .uploadedAt(LocalDateTime.now())
        .build());
  }

  public byte[] compressImage(byte[] imageData) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
    BufferedImage image = ImageIO.read(bais);
    if (image == null) {
      throw new IOException("이미지 데이터가 아닙니다.");
    }

    // JPEG 이미지 압축 품질 설정
    float quality = 0.75f;
    ByteArrayOutputStream compressed = new ByteArrayOutputStream();
    ImageIO.write(image, "jpg", compressed);
    byte[] compressedImage = compressed.toByteArray();

    bais.close();
    compressed.close();

    return compressedImage;
  }

  private byte[] createThumbnail(byte[] imageData, int width, int height) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
    BufferedImage image = ImageIO.read(bais);
    if (image == null) {
      throw new IOException("이미지 데이터가 아닙니다.");
    }

    BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = thumbnailImage.createGraphics();

    // 이미지 크기 조정
    graphics2D.drawImage(image, 0, 0, width, height, null);
    graphics2D.dispose();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(thumbnailImage, "jpg", baos);
    byte[] thumbnailData = baos.toByteArray();

    bais.close();
    baos.close();

    return thumbnailData;
  }
}
