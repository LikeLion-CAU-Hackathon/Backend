package com.example.santa.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerRequestDto {
    private String contents;

    public AnswerRequestDto(String contents) {
        this.contents = contents;
    }
}
