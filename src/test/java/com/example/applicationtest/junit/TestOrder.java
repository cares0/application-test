package com.example.applicationtest.junit;

import org.junit.jupiter.api.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrder {

    private int value = 0;

    @Test
    @Order(3)
    void method1() {
        value++;
        System.out.println("test method 1");
        System.out.println("value = " + value);
        System.out.println("this = " + this);
    }

    @Test
    @Order(1)
    void method2() {
        value++;
        System.out.println("test method 2");
        System.out.println("value = " + value);
        System.out.println("this = " + this);
    }

    @Test
    @Order(2)
    void method3() {
        value++;
        System.out.println("test method 3");
        System.out.println("value = " + value);
        System.out.println("this = " + this);
    }

}
