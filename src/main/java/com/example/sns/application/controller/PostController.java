package com.example.sns.application.controller;

import com.example.sns.application.usacase.CreatePostUsecase;
import com.example.sns.application.usacase.GetTimeLinePostsUsacase;
import com.example.sns.domain.post.dto.DailyPostCount;
import com.example.sns.domain.post.dto.DailyPostCountRequest;
import com.example.sns.domain.post.dto.PostCommand;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.service.PostReadService;
import com.example.sns.domain.post.service.PostWriteService;
import com.example.sns.util.CursorRequest;
import com.example.sns.util.PageCursor;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimeLinePostsUsacase getTimeLinePostsUsacase;
    private final CreatePostUsecase createPostUsecase;

    @PostMapping("")
    public Long create(PostCommand command) {
         return createPostUsecase.execute(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
           Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }
    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable Long memberId,
           CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeLine(
            @PathVariable Long memberId,
           CursorRequest cursorRequest
    ) {
        return getTimeLinePostsUsacase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable Long postId) {
        postWriteService.likePost(postId);
    }
}
