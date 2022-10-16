package com.example.applicationtest;

import org.junit.jupiter.api.*;

public class BasicAnnotation {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("before all");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("before each");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("after all");
    }

    @AfterEach
    public void AfterEach() {
        System.out.println("after each");
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }

    /**
     * before all
     * before each
     * test1
     * after each
     * before each
     * test2
     * after each
     * after all
     */
}
