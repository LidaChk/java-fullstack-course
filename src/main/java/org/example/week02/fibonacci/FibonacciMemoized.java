package org.example.week02.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoized {
    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: O(n) - single calculation from 0 to n
     * Space Complexity: O(n) - n size HashMap and n size stack
     *
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */



    public static long fibonacciMemoized(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        Map<Integer, Long> cache = new HashMap<>();
        return fibonacciMemoizedHelper(n, cache);

    }

    private static long fibonacciMemoizedHelper(int n, Map<Integer, Long> cache) {

        if (n < 2) {
            return n;
        }

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        long result = fibonacciMemoizedHelper(n - 1, cache) + fibonacciMemoizedHelper(n - 2, cache);
        cache.put(n, result);
        return result;
    }

}
