package com.example.sns.application.usacase;

import com.example.sns.domain.follow.service.FollowWriteService;
import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsacase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 memberId로 회원조회
            2. FollowWriteService.create()
         */
        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);
        followWriteService.create(fromMember,toMember);
    }
}
