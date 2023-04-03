package com.example.sns.domain.member.service;

import com.example.sns.domain.member.dto.RegisterMemberCommand;
import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    public Member create(RegisterMemberCommand command){
        /*
         요구사항
        - 회원정보 (이메일, 닉네임, 생년월일)을 등록한다.
        - 닉네임은 10자를 넘길 수 없다.

         파라미터 - memberResisterCommand

         val member = Member.of(memberRegisterCommand)
         memberRepository.save(member)
        * */
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();
        System.out.println("======service Member create===========");
        return memberRepository.save(member);
    }

    public void changeNickname(Long memberId, String nickname){
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeName(nickname);
        memberRepository.save(member);
    }
}
