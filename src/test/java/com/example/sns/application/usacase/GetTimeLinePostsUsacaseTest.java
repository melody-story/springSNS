package com.example.sns.application.usacase;

import com.example.sns.domain.follow.entity.Follow;
import com.example.sns.domain.follow.repository.FollowRepository;
import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.repository.MemberRepository;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class GetTimeLinePostsUsacaseTest {

    @Autowired
    GetTimeLinePostsUsacase getTimeLinePostsUsacase;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void getFollowsPostsTest() throws Exception {
        //given

        Member member1 = Member.builder()
                .nickname("Melody")
                .email("00000@gmail.com")
                .birthday(LocalDate.of(2023,1,1))
                .build();
        Member member3 = Member.builder()
                .nickname("Melody")
                .email("00000@gmail.com")
                .birthday(LocalDate.of(2023,2,2))
                .build();
        Member member2 = Member.builder()
                .nickname("Caleb")
                .email("1111111@gmail.com")
                .birthday(LocalDate.of(2023,3,3))
                .build();

        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Member savedMember3 = memberRepository.save(member3);

        //when
        Follow follow1 = Follow.builder()
                .fromMemberId(savedMember3.getId())
                .toMemberId(savedMember1.getId())
                .build();
        Follow follow2 = Follow.builder()
                .fromMemberId(savedMember3.getId())
                .toMemberId(savedMember2.getId())
                .build();
        var SIZE = 8;

        followRepository.save(follow1);
        followRepository.save(follow2);
        for (int i = 0; i < 5; i++) {
            postRepository.save(Post.builder().memberId(savedMember1.getId()).contents("게시물"+i).build());
            postRepository.save(Post.builder().memberId(savedMember2.getId()).contents("게시물"+i).build());
        }

        //then
        List<Post> posts = postRepository.findAllByInMemberIdAndOrderByIdDesc(List.of(savedMember1.getId(), savedMember2.getId()), SIZE);
        Assertions.assertEquals(posts.size(),SIZE);
    }

}