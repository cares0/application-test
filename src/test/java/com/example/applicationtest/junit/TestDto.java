package com.example.applicationtest.junit;

import lombok.Data;

@Data
public class TestDto {

    private String name;

    private Integer age;

    public TestDto(String name) {
        this.name = name;
    }

    public TestDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

