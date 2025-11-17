package com.example.santa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AnswerListDto {
    private Long answerId;
    private String userNickname;
    private String contents;
    private LocalDateTime createdTime;
    private long likeCount;
    private long replyCount;
}