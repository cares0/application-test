package com.example.applicationtest.junit;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InstanceTest {
    
    private int value = 0;
    
    @Test
    void method1() {
        value++;
        System.out.println("value = " + value); // value = 1
        System.out.println("this = " + this);
    }

    @Test
    void method2() {
        value++;
        System.out.println("value = " + value); // value = 1
        System.out.println("this = " + this);
    }

    @BeforeAll
    public void beforeAll() {
        System.out.println("before value = " + value);
    }

    @AfterAll
    public void afterAll() {
        System.out.println("after value = " + value);
    }

}
