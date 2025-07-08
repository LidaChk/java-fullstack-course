package org.example.week02.fibonacci;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FibonacciCorrectnessTest {

    private static final int[] TEST_INPUTS = { 0, 1, 2, 5, 10, 20, 30, 35, 40 };
    private static final long[] EXPECTED_OUTPUTS = { 0, 1, 1, 5, 55, 6765, 832040, 9227465, 102334155 };

    @Test
    @DisplayName("Test correctness with known Fibonacci values")
    void correctnessTest() {

        for (int i = 0; i < TEST_INPUTS.length; i++) {
            assertFibonacciImplementations(TEST_INPUTS[i], EXPECTED_OUTPUTS[i]);
        }
    }

    private void assertFibonacciImplementations(int n, long expected) {

        assertEquals(expected, FibonacciIterative.fibonacciIterative(n),
                "Iterative implementation failed for n=" + n);
        assertEquals(expected, FibonacciMemoized.fibonacciMemoized(n),
                "Memoized implementation failed for n=" + n);
        assertEquals(expected, FibonacciRecursive.fibonacciRecursive(n),
                "Recursive implementation failed for n=" + n);

    }

    @Test
    @DisplayName("Test negative input handling")
    void testNegativeInputs() {
        int n = -1;

        assertThrows(IllegalArgumentException.class, () -> FibonacciRecursive.fibonacciRecursive(n));
        assertThrows(IllegalArgumentException.class, () -> FibonacciMemoized.fibonacciMemoized(n));
        assertThrows(IllegalArgumentException.class, () -> FibonacciIterative.fibonacciIterative(n));
    }
}
