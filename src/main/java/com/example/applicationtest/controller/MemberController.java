package com.example.applicationtest.controller;

import com.example.applicationtest.domain.Member;
import com.example.applicationtest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{memberId}")
    public Member memberDetail(@PathVariable("memberId") Long memberId) {
        return memberService.getOne(memberId);
    }

    @PostMapping("/members")
    public Long memberSave(@RequestBody MemberSaveRequest memberSaveRequest) {
        return memberService.save(memberSaveRequest);
    }
}
