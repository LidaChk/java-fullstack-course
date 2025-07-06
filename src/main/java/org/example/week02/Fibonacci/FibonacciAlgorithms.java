package org.example.week02.Fibonacci;

public class FibonacciAlgorithms {
    /**
     * Recursive implementation of Fibonacci sequence
     * Time Complexity: {todo: specify. explain}
     * Space Complexity: {todo: specify.explain }
     *
     * Explanation: Each call branches into two recursive calls, creating
     * a binary tree of calls with height n. The same subproblems are
     * solved multiple times, leading to exponential time complexity.
     */
    public static long fibonacciRecursive(int n) {
        // todo: Your implementation here
    }

    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: {todo: specify. explain}
     * Space Complexity: {todo: specify.explain }
     *
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */
    public static long fibonacciMemoized(int n) {
        // todo: Your implementation here
    }

    /**
     * Iterative implementation of Fibonacci sequence
     * Time Complexity: O(n) - single loop from 0 to n
     * Space Complexity: O(1) - constant space usage
     *
     * Explanation: Uses bottom-up approach with only two variables
     * to track previous values, eliminating recursion overhead.
     */
    public static long fibonacciIterative(int n) {
        // Your implementation here
    }
}
