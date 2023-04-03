package com.example.sns.domain.member;

import com.example.sns.domain.member.entity.Member;
import com.example.sns.domain.member.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {
    @Test
    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    public void tesChangeName() throws Exception {
        //given
        Member member = MemberFixtureFactory.create();
        var expected = "Melogy";

        //when
        member.changeName(expected);

        //then
        Assertions.assertEquals(member.getNickname(), expected);
    }

    @Test
    @DisplayName("회원 닉네임은 10글자를 초과할 수 없다.")
    public void testNicknameMaxLength() throws Exception {
        //given
        Member member = MemberFixtureFactory.create();
        String overMaxLengthhName = "MelodyMelodyMelodyMelodyMelody";

        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeName(overMaxLengthhName)
        );

        //then
    }
}
