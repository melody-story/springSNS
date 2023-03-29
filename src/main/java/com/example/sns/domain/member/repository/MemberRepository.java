package com.example.sns.domain.member.repository;

import com.example.sns.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.security.spec.NamedParameterSpec;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    static final private String TABLE = "member";
    public Optional<Member> findById(Long id){
        /*
        * select * from Member
        * where id = : id
        * */
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        RowMapper<Member> rowMapper = (ResultSet result, int rowNum) -> Member
                .builder()
                .id(result.getLong("id"))
                .email(result.getString("email"))
                .nickname(result.getString("nickname"))
                .birthday(result.getObject("birthday", LocalDate.class))
                .createdAt (result.getObject("createdAt", LocalDateTime.class))
                .build();
        Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);

    }

    public Member save(Member member){
        /*
        * member id를 보고 갱신 또는 삽입을 정함
        * 반환값은 id를 담아서 반환한다.
        * */
        System.out.println("======repository Member save===========");
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    public Member insert(Member member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        System.out.println("======repository Member insert===========");
        return Member
                .builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();


    }

    public Member update(Member member) {
        return member;
    }
}
