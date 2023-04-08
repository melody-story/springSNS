package com.example.sns.application.usacase;

import com.example.sns.domain.follow.entity.Follow;
import com.example.sns.domain.follow.service.FollowReadService;
import com.example.sns.domain.post.dto.PostCommand;
import com.example.sns.domain.post.service.PostWriteService;
import com.example.sns.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    private final PostWriteService postWriteService;

    private final FollowReadService followReadService;

    private final TimelineWriteService timelineWriteService;

    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);
        var followMemberIds = followReadService.getFollowers(postCommand.memberId())
                .stream()
                .map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followMemberIds);

        return postId;
    }

}
