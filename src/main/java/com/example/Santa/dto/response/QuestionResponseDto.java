package com.example.Santa.dto.response;

import lombok.Getter;

@Getter
public class QuestionResponseDto {
    private final String content;

    public QuestionResponseDto(String content) {
        this.content = content;
    }
}
