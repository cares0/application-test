package com.example.applicationtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ExtensionTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension
            = new FindSlowTestExtension(1000L);
    
    @Test
    void slow_test1() throws Exception {
        Thread.sleep(1000L);
    }

    @Test
    @SlowTest
    void slow_test2() throws Exception {
        Thread.sleep(1000L);
    }
    
}
