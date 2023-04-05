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
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        var stopWatch = new StopWatch();

        stopWatch.start();

        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 100)
            .parallel()
            .mapToObj(i -> easyRandom.nextObject(Post.class))
            .toList();

        stopWatch.stop();
        System.out.println("객체생성시간 : " + stopWatch.getTotalTimeSeconds());

        StopWatch queryStopwatch = new StopWatch();
        /*
            많은 건수의 값을 생성할 때,
            JPA 는 각각을 단건으로 쿼리 날리기 떄문에, 하나의 쿼리로 생성하기위해 jdbcTemplate 을 사용한다.
         */
        queryStopwatch.start();

        postRepository.bulkInsert(posts);

        queryStopwatch.stop();
        System.out.println("DB 쿼리 인서트 시간 = " + queryStopwatch.getTotalTimeSeconds());

    }
}
