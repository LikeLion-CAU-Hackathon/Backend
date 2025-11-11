package com.example.santa.dto.response;

import com.example.santa.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AnswerResponseDto {
    private Long id;
    private String contents;
    private String userName;        // 작성자 이름
    private LocalDateTime createdTime;


    public static AnswerResponseDto fromEntity(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .contents(answer.getContents())
                .userName(answer.getAppUser().getName()) // AppUser에 따라 name 필드명 맞게 수정
                .createdTime(answer.getCreatedTime())
                .build();
    }
}
