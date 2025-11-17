package com.example.santa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReplyResponseDto {
    private Long replyId;
    private Long answerId;
    private Long userId;
    private String userNickname;
    private String text;
    private LocalDateTime createdTime;
}