package com.example.sns.domain.member.dto;

import java.time.LocalDateTime;

public record PostDto(
        Long Id,
        String contents,
        LocalDateTime createAt,
        Long likeCount
) {
}