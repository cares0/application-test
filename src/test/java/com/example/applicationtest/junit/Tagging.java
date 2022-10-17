package com.example.applicationtest.junit;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class Tagging {

    @Test
    @Tag("fast")
    public void fast() {
        System.out.println("fast test");
    }

    @Test
    @Tag("slow")
    public void slow() {
        System.out.println("slow test");
    }

    @FastTest
    public void fastAnnotation() {
        System.out.println("fast annotation");
    }


}
