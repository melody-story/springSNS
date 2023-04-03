package com.example.sns.domain.member.service;

import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;

    public MemberDto getMember(Long id) {
        return toDto(memberRepository.findById(id).orElseThrow());
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(),member.getNickname(), member.getBirthday() );
    }
}
