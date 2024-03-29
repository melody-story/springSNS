package com.example.sns.domain.post.service;

import com.example.sns.domain.member.dto.PostDto;
import com.example.sns.domain.post.dto.DailyPostCount;
import com.example.sns.domain.post.dto.DailyPostCountRequest;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostLikeRepository;
import com.example.sns.domain.post.repository.PostRepository;
import com.example.sns.util.CursorRequest;
import com.example.sns.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostReadService  {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
             반환값 ->  리스트[ 작성일자, 작성회원, 작성 게시물 갯수]
             select createdDate memberId, count(id)
             from Post
             where memberId =:memberId and createdDate between firstDate and lastDate
             group by createdDate memberId
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageRequest) {
         return postRepository.findAllByMemberId(memberId,pageRequest).map(this::toDto);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    public PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                postLikeRepository.count(post.getId())
        );
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        /*
         member 1 명의 postList 조회
         */
        List<Post> posts = findAllBy(memberId, cursorRequest);
        Long nextKey = getNextKey(posts);
    return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        /*
         member 1 명의 postList 조회
         */
        List<Post> posts = findAllBy(memberIds, cursorRequest);
        Long nextKey = getNextKey(posts);
    return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }


    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        } else {
            return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
        }
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
