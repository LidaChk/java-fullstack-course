package org.example.week02.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoized {
    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: O(n) - single calculation from 0 to n
     * Space Complexity: O(n) - n size hashmap and n size stack
     * <p>
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */

    private FibonacciMemoized() {
    }

    public static long fibonacciMemoized(int n) {

        Map<Integer, Long> cache = new HashMap<>();

        if (n < 0)
            throw new IllegalArgumentException();

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
