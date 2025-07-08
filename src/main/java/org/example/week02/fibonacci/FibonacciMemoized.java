package org.example.week02.fibonacci;

public class FibonacciMemoized {

    public static void main(String[] args) {
        int n = 9;
        System.out.println("fibonacciMemoized(" + n + ") = " + fibonacciMemoized(n));

    }

    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: O(n) - single loop from 0 to n
     * Space Complexity: O(n) - n size Array
     *
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */

    private static long[] cache;

    public static long fibonacciMemoized(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        if (n < 2) {
            return n;
        }

        cache = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i == 0 || i == 1) {
                cache[i] = i;
            } else {
                cache[i] = cache[i - 1] + cache[i - 2];
            }
        }
        return cache[n];
    }

}
