package com.example.sns.application.controller;


import com.example.sns.application.usacase.CreateFollowMemberUsacase;
import com.example.sns.application.usacase.GetFollowingMembersUsacase;
import com.example.sns.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberUsacase createFollowMemberUsacase;
    private final GetFollowingMembersUsacase getFollowingMembersUsacase;

    @PostMapping("/{fromId}/{toId}")
    public void register(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsacase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}/")
    public List<MemberDto> followingList(@PathVariable Long fromId) {
        return getFollowingMembersUsacase.execute(fromId);
    }


}
