package com.example.sns.domain.post.repository;

import com.example.sns.domain.post.entity.PostLike;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostLikeRepository {
    /*
        저장하는 부분과
        회원아이디와 커서를 받아 커서처리하는 부분이 필요하다.
    */

    static final String TABLE = "PostLike";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<PostLike> ROW_MAPPER = (ResultSet resultSet, int rowNum)
        -> PostLike.builder()
        .id(resultSet.getLong("id"))
        .memberId(resultSet.getLong("memberId"))
        .postId(resultSet.getLong("postId"))
        .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
        .build();

    public PostLikeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long count(Long postId) {
        var sql = String.format("""
            SELECT count(id)
            FROM %s
            WHERE postId = :postId;
            """, TABLE);
        var params = new MapSqlParameterSource().addValue("postId", postId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public PostLike save(PostLike postLike) {
        if (postLike.getId() == null) {
            return insert(postLike);
        }
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }


    private PostLike insert(PostLike postLike) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
            .withTableName(TABLE)
            .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();
        return PostLike.builder()
            .id(id)
            .memberId(postLike.getMemberId())
            .postId(postLike.getPostId())
            .createdAt(postLike.getCreatedAt())
            .build();
    }

}
