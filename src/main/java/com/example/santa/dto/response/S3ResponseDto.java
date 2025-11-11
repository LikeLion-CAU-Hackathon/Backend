package com.example.santa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3ResponseDto {
    private Long id;
    private String imageUrl;
    private String message;
}