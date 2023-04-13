package com.example.sns.domain.post.service;

import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.post.dto.PostCommand;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.entity.PostLike;
import com.example.sns.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        var postLike = PostLike
                .builder()
                .memberId(memberDto.id())
                .postId(post.getId())
                .build();
        return postLikeRepository.save(postLike).getPostId();
    }
}
