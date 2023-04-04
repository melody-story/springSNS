package com.example.sns.application.usacase;

import com.example.sns.domain.follow.entity.Follow;
import com.example.sns.domain.follow.repository.FollowRepository;
import com.example.sns.domain.follow.service.FollowReadService;
import com.example.sns.domain.follow.service.FollowWriteService;
import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsacase {
    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        /*
            1. fromMemberId = memberId -0> FollowL ist
            2. 1번을 훈회하면서 회원정보를 찾으면된다.
         */
        List<Follow> followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(follow -> follow.getToMemberId()).toList();
        return  memberReadService.getMembers(followingMemberIds);
    }

}
