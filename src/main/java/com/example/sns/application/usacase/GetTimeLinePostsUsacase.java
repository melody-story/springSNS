package com.example.sns.application.usacase;

import com.example.sns.domain.follow.entity.Follow;
import com.example.sns.domain.follow.service.FollowReadService;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostRepository;
import com.example.sns.domain.post.service.PostReadService;
import com.example.sns.util.CursorRequest;
import com.example.sns.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimeLinePostsUsacase {
    /* 고려사항
        1. Post 와 Follow 도메인 두가지 필요
        2. 무한 스크롤 방식인 Cursor 기변 페이징 구현

       Fan Out On Write (Push model) : 쓰기 시점에 부화가 발생
     */

    private final PostReadService postReadService;
    private final FollowReadService followReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
       /*
            순서
            1. memberId로 Follow 조회
            2. 1번의 결과로 게시물 조회

           시간 복잡도
           log(Follow 전체 레코드) + 해당회원의 Following 회원수  * log(Post 전체 레코드)

            Fan Out On Read (Pull model) : 조회 시점에 부화가 발생
        */
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /*
            순서
            1. Timeline 테이블 조회
            2. 1번에 해당하는 게시물을 조회
         */
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

}
