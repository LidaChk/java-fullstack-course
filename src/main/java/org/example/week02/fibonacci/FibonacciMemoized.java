package org.example.week02.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoized {
    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: O(n) - single calculation from 0 to n
     * Space Complexity: O(n) - n size HashMap and n size stack
     * <p>
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */


    public static long fibonacciMemoized(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        Long[] cache = new Long[n + 1];
        return fibonacciMemoizedHelper(n, cache);

    }

    private static long fibonacciMemoizedHelper(int n, Long[] cache) {

        if (n < 2) {
            return n;
        }

        if (cache[n] != null) {
            return cache[n];
        }

        long result = fibonacciMemoizedHelper(n - 1, cache) + fibonacciMemoizedHelper(n - 2, cache);
        cache[n] = result;
        return result;
    }

}
