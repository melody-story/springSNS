package com.example.sns.domain.follow.service;

import com.example.sns.domain.follow.entity.Follow;
import com.example.sns.domain.follow.repository.FollowRepository;
import com.example.sns.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FollowWriteService {
    private final FollowRepository followRepository;

    public void create(MemberDto fromMember, MemberDto toMember) {

    /*
        from , to 회원 정보를 받아서
        저장할텐데
        from <-> to validate
     */
        Assert.isTrue(!fromMember.id().equals(toMember.id()),"From, To 회원이 동일합니다. ");
        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        Follow save = followRepository.save(follow);
    }
}
