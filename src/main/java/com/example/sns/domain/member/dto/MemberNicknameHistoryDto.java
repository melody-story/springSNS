package com.example.sns.domain.member.dto;

import java.time.LocalDateTime;

public record MemberNicknameHistoryDto(
        Long Id,
        Long memberId,
        String nickname,
        LocalDateTime createdAt

) {
}
