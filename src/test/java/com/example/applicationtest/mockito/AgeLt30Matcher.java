package com.example.applicationtest.mockito;

import org.mockito.ArgumentMatcher;

public class AgeLt30Matcher implements ArgumentMatcher<Integer> {

    @Override
    public boolean matches(Integer argument) {
        if (argument < 30) {
            return true;
        }
        return false;
    }
}
