package com.example.sns.domain.post.service;

import com.example.sns.domain.post.dto.PostCommand;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post
                .builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        return postRepository.save(post).getId();
    }

    @Transactional
    public void likePost(Long postId) {
        //게시물 조회후 like 업데이트
        var post = postRepository.findById(postId, true).orElseThrow();
        post.incrementLikeCount();
         postRepository.save(post);
    }
}
