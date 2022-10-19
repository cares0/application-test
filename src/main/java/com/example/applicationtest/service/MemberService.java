package com.example.applicationtest.service;

import com.example.applicationtest.controller.MemberSaveRequest;
import com.example.applicationtest.domain.Member;
import com.example.applicationtest.domain.MemberLevel;
import com.example.applicationtest.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    public MemberRepository memberRepository;

    public LevelCalculator levelCalculator;

    public MemberService(MemberRepository memberRepository, LevelCalculator levelCalculator) {
        this.memberRepository = memberRepository;
        this.levelCalculator = levelCalculator;
    }

    public MemberLevel updateLevel(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("ID " + memberId + " Member Not Found"));

        MemberLevel memberLevel = levelCalculator.calculateMemberLevel(member.getAge());

        member.updateLevel(memberLevel);

        return memberLevel;
    }

    public Member getOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member Not Found"));
    }

    public Long save(MemberSaveRequest memberSaveRequest) {
        Member member = Member.builder()
                .name(memberSaveRequest.getName())
                .age(memberSaveRequest.getAge())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

}
