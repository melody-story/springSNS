package com.example.sns.domain.member.dto;

import java.time.LocalDate;
// record : java 14부터 사용가능 property 의 getter setter를 property이름을 가진 메서드로 사용할 수 있다.
public record RegisterMemberCommand (
        String email,
        String nickname,
        LocalDate birthday){
}