package org.example.week02.fibonacci;

public class FibonacciIterative {
    /**
     * Iterative implementation of Fibonacci sequence
     * Time Complexity: O(n) - single loop from 0 to n
     * Space Complexity: O(1) - constant space usage
     *
     * Explanation: Uses bottom-up approach with only two variables
     * to track previous values, eliminating recursion overhead.
     */
    public static long fibonacciIterative(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        if (n < 2) {
            return n;
        }

        long prev = 0;
        long current = 1;

        for (int i = 2; i <= n; i++) {
            long temp = current;
            current = prev + current;
            prev = temp;
        }
        return current;
    }
}
