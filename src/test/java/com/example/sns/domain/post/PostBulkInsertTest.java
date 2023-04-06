package com.example.sns.domain.post;

import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostRepository;
import com.example.sns.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                2L,
                LocalDate.of(1970, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        int _1만 = 10000;
        var stopWatch = new StopWatch();

        stopWatch.start();
        List<Post> posts = IntStream.range(0, _1만 * 200)
            .parallel()
            .mapToObj(i -> easyRandom.nextObject(Post.class))
            .toList();
        stopWatch.stop();

        StopWatch queryStopwatch = new StopWatch();

        queryStopwatch.start();
        postRepository.bulkInsert(posts);
        queryStopwatch.stop();

        System.out.println("객체생성시간 : " + stopWatch.getTotalTimeSeconds());
        System.out.println("DB 쿼리 인서트 시간 = " + queryStopwatch.getTotalTimeSeconds());

    }
}
