package com.example.applicationtest.service;

import com.example.applicationtest.domain.MemberLevel;

public interface LevelCalculator {

    MemberLevel calculateMemberLevel(int age);

}
