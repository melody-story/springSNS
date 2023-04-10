package com.example.sns.domain.member.service;

import com.example.sns.domain.member.dto.RegisterMemberCommand;
import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.entity.MemberNicknameHistory;
import com.example.sns.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.sns.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    /*
    * 트랜잭션
    * 1. @Transaction 어노테이션 샤용
    *  : proxy 패턴으로 동작하기 때문에 inner함수에 선언된 transaction은 작동하지 않는다.
    * 2. TransactionTemplate 사용
    * 3. sql query에 저굥ㅇ
    * " START TRANSACTION;
    * COMMIT;
    * "
    *
    * */

    @Transactional // 1. 선언
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
        Member savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname){
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeName(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }

}
