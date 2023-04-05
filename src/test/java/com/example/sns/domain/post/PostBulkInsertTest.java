package com.example.sns.domain.post;

import com.example.sns.SnsApplication;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.repository.PostRepository;
import com.example.sns.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
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

        IntStream.range(0,5)
                .mapToObj(i->
                        easyRandom.nextObject(Post.class))
                .forEach(x->
                                postRepository.save(x)
                );
    }
}
