package org.example.week02.testrunner.sampletest;

import org.example.week02.testrunner.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {

    @Test
    void passedTest() {
        var expected = 1;
        var result = 1;
        assertEquals(expected, result);
    }

    @Test
    void failedTest() {
        var expected = 1;
        var result = -1;
        assertEquals(expected, result);
    }

}
