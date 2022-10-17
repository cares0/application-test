package com.example.applicationtest.mockito;

import org.mockito.ArgumentMatcher;

public class GoeMatcher implements ArgumentMatcher<Integer> {

    private Integer numToCompare;

    public GoeMatcher(Integer numToCompare) {
        this.numToCompare = numToCompare;
    }

    @Override
    public boolean matches(Integer argument) {
        if (argument >= numToCompare) {
            return true;
        }
        return false;
    }
}
