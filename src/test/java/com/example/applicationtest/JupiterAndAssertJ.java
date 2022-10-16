package com.example.applicationtest;

import org.junit.jupiter.api.*;

@DisplayName("test class")
class JupiterAndAssertJ {

    @Test
    @DisplayName("jupiter test")
    void jupiter_test() {
        int num = 1;
        org.junit.jupiter.api.Assertions.assertEquals(num, 2, "1은 1과 같다.");
    }

    @Test
    @DisplayName("assertj test")
    void assertj_test() {
        int num = 1;
        org.assertj.core.api.Assertions.assertThat(num).as("%d은 1과 같다.", num).isEqualTo(2);
    }

}