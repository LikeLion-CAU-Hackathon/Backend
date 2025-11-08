package com.example.Santa.controller;

import com.example.Santa.domain.Image;
import com.example.Santa.dto.response.S3ResponseDto;
import com.example.Santa.repository.ImageRepository;
import com.example.Santa.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadImage(file);
            return "File uploaded successfully! imageUrl: " + imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "File upload failed!";
        }
    }

    @GetMapping("/get")
    public ResponseEntity<S3ResponseDto> getImage(@RequestParam("id") long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 존재하지 않습니다."));

        String url = image.getUrl();

        if (url == null || url.isBlank()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new S3ResponseDto(id, null, "이미지 URL이 등록되어 있지 않습니다."));
        }

        return ResponseEntity.ok(new S3ResponseDto(id, url, "이미지 조회 성공"));
    }

}
