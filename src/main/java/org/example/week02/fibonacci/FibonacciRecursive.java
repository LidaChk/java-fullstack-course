package org.example.week02.fibonacci;

public class FibonacciRecursive {
    /**
     * Recursive implementation of Fibonacci sequence
     * Time Complexity: O(2^n) from binary tree.
     * At each level of recursion, the number of calls doubles.
     * Space Complexity: O(n) due to recursion depth
     *
     * Explanation: Each call branches into two recursive calls, creating
     * a binary tree of calls with height n. The same subproblems are
     * solved multiple times, leading to exponential time complexity.
     */
    public static long fibonacciRecursive(int n) {

        if (n < 0)
            throw new IllegalArgumentException();

        if (n < 2) {
            return n;
        }

        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
}
