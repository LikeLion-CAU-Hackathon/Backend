package com.example.Santa.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Santa.domain.Image;
import com.example.Santa.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3, ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.imageRepository = imageRepository;
    }

    /**
     * S3에 이미지 업로드 하기
     */
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // s3 업로드
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata));

        // URL 생성
        String url = amazonS3.getUrl(bucket, fileName).toString();

        // DB에 저장
        Image savedImage = new Image(url);
        imageRepository.save(savedImage);

        return url; // 컨트롤러에서 클라이언트에게 반환
    }


}



