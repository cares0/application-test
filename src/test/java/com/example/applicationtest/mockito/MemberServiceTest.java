package com.example.applicationtest.mockito;

import com.example.applicationtest.domain.Member;
import com.example.applicationtest.domain.MemberLevel;
import com.example.applicationtest.repository.MemberRepository;
import com.example.applicationtest.service.LevelCalculator;
import com.example.applicationtest.service.MemberService;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    LevelCalculator levelCalculator;

    @Test
    void juniorTest() {
        // given
        Long memberId = 10L;

        // stub
        Integer stubbedMemberAge = 10;
        MemberLevel stubbedMemberLevel = MemberLevel.JUNIOR;
        Member stubbedMember = Member.builder()
                .id(10L)
                .age(stubbedMemberAge)
                .build();

        when(memberRepository.findById(eq(memberId)))
                .thenReturn(Optional.of(stubbedMember));

        when(levelCalculator.calculateMemberLevel(intThat(new AgeLt30Matcher())))
                .thenReturn(stubbedMemberLevel);

        // when
        MemberLevel memberLevel = memberService.updateLevel(memberId);

        // then
        assertThat(memberLevel).isEqualTo(stubbedMemberLevel);
    }

    @Test
    void customMatcherTest() {
        // given
        Long memberId = 10L;

        // stub
        Integer stubbedMemberAge = 50;
        MemberLevel stubbedMemberLevel = MemberLevel.MASTER;
        Member stubbedMember = Member.builder()
                .id(10L)
                .age(stubbedMemberAge)
                .build();

        when(memberRepository.findById(eq(memberId)))
                .thenReturn(Optional.of(stubbedMember));

        // 필드가 없는 경우
        when(levelCalculator.calculateMemberLevel(ArgumentMatchers.intThat(new AgeLt30Matcher())))
                .thenReturn(stubbedMemberLevel);

        // 필드가 있는 경우
        when(levelCalculator.calculateMemberLevel(intThat(new GoeMatcher(50))))
                .thenReturn(stubbedMemberLevel);

        // when
        MemberLevel memberLevel = memberService.updateLevel(memberId);

        // then
        assertThat(memberLevel).isEqualTo(stubbedMemberLevel);
    }

    @Test
    void repeatTest() {
        // given
        Long memberId = 10L;

        // stub
        Integer stubbedMemberAge = 50;
        MemberLevel stubbedMemberLevel1 = MemberLevel.JUNIOR;
        MemberLevel stubbedMemberLevel2 = MemberLevel.MASTER;
        Member stubbedMember = Member.builder()
                .id(10L)
                .age(stubbedMemberAge)
                .build();

        when(memberRepository.findById(eq(memberId)))
                .thenReturn(Optional.of(stubbedMember));

        when(levelCalculator.calculateMemberLevel(anyInt()))
                .thenReturn(stubbedMemberLevel1)
                .thenThrow(new RuntimeException())
                .thenReturn(stubbedMemberLevel2);

        // when then
        assertThat(memberService.updateLevel(memberId)).isEqualTo(stubbedMemberLevel1);
        assertThatThrownBy(() -> memberService.updateLevel(memberId)).isInstanceOf(RuntimeException.class);
        assertThat(memberService.updateLevel(memberId)).isEqualTo(stubbedMemberLevel2);
    }

    @Test
    void dbExceptionTest() {
        // given
        Long memberId = 20L;

        // stub
        doThrow(new TransactionException("transaction ex"))
                .when(memberRepository).findById(eq(memberId));

        // when then
        assertThatThrownBy(() -> memberService.updateLevel(memberId))
                .isInstanceOf(TransactionException.class);
    }
}
