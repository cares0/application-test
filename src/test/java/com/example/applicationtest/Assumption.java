package com.example.applicationtest;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import org.springframework.core.env.Environment;

public class Assumption {

    @Test
    @DisplayName("assumption test")
    void assumption_test() {
        int num1 = 1;
        int num2 = 2;
        int expected = 2;

        Assumptions.assumeThat(num1).isEqualTo(expected);
        Assertions.assertThat(num2).isEqualTo(expected);
    }

    @Test
    @DisplayName("assertion test")
    void assertion_test() {
        int num1 = 1;
        int num2 = 2;
        int expected = 2;

        Assertions.assertThat(num1).isEqualTo(expected);
        Assertions.assertThat(num2).isEqualTo(expected);
    }

    @Test
    @DisplayName("assumption test ex")
    void assumption_test_ex() {
        String osName = System.getProperty("os.name");
        Assumptions.assumeThat(osName).isEqualTo("window 10");

        int num = 1;
        int expected = 2;
        Assertions.assertThat(num).isEqualTo(expected);
    }

    @Test
    @EnabledOnOs(OS.MAC)
    @EnabledOnJre(JRE.JAVA_11)
    @EnabledIfEnvironmentVariable(named = "test.env", matches = "test")
    @DisplayName("assumption test annotation")
    void assumption_test_annotation() {
        int num = 1;
        int expected = 2;
        Assertions.assertThat(num).isEqualTo(expected);
    }

}
