package com.example.sns.domain.member.repository;

import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "MemberNicknameHistory";

    RowMapper<MemberNicknameHistory> rowMapper = (ResultSet result, int rowNum) -> MemberNicknameHistory
            .builder()
            .id(result.getLong("id"))
            .memberId(result.getLong("memberId"))
            .nickname(result.getString("nickname"))
            .createdAt (result.getObject("createdAt", LocalDateTime.class))
            .build();

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var params = new MapSqlParameterSource().addValue("memberId",memberId);
        return namedParameterJdbcTemplate.query(sql,params,rowMapper);
    }

    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory){
        /*
        * memberNicknameHistory id를 보고 갱신 또는 삽입을 정함
        * 반환값은 id를 담아서 반환한다.
        * */
        System.out.println("======repository MemberNicknameHistory save===========");
        if (memberNicknameHistory.getId() == null) {
            return insert(memberNicknameHistory);
        }
        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");
    }

    public MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        System.out.println("======repository MemberNicknameHistory insert===========");
        return MemberNicknameHistory
                .builder()
                .id(id)
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .createdAt(memberNicknameHistory.getCreatedAt())
                .build();


    }

}
