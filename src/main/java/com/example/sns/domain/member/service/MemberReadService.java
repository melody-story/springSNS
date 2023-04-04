package com.example.sns.domain.member.service;

import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.member.dto.MemberNicknameHistoryDto;
import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.entity.MemberNicknameHistory;
import com.example.sns.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.sns.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        return toDto(memberRepository.findById(id).orElseThrow());
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(),member.getNickname(), member.getBirthday() );
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        List<Member> members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(member -> toDto(member)).toList();
    }

    public List<MemberNicknameHistoryDto> getMemberNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(history -> toDto(history))
                .toList();
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }

}
