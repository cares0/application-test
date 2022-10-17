package com.example.applicationtest.service;

import com.example.applicationtest.domain.MemberLevel;

public class NormalLevelCalculator implements LevelCalculator {

    @Override
    public MemberLevel calculateMemberLevel(int age) {
        if (age >= 0 && age < 30) {
            return MemberLevel.JUNIOR;
        } else if (age >= 30 && age < 50) {
            return MemberLevel.SENIOR;
        } else {
            return MemberLevel.MASTER;
        }
    }

}
