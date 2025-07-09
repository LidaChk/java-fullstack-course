package org.example.week02.fibonacci;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class FibonacciBenchmarkTest {

    private static final int[] TEST_INPUTS = { 10, 20, 30, 35, 40 };
    private static final Map<String, Map<Integer, Long>> executionTimes = new HashMap<>();
    private static final Map<String, Map<Integer, Long>> memoryUsages = new HashMap<>();
    private static final LinkedHashMap<String, Function<Integer, Long>> tasks = new LinkedHashMap<>();


    static Stream<Integer> fibonacciInputs() {
        return Arrays.stream(TEST_INPUTS).boxed();
    }
    @BeforeAll
    static void setUpAll() {

        tasks.put("recursive", FibonacciRecursive::fibonacciRecursive);
        tasks.put("memoized", FibonacciMemoized::fibonacciMemoized);
        tasks.put("iterative", FibonacciIterative::fibonacciIterative);

        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            String key = task.getKey();
            executionTimes.put(key, new HashMap<>());
            memoryUsages.put(key, new HashMap<>());
        }
    }

    @BeforeEach
    void setUp() {
        System.gc();
    }

    @AfterEach
    void tearDown() {
        System.gc();
    }

    @AfterAll
    static void tearDownAll() {
        printPerformanceResults();
        printMemoryUsageResults();
    }


    @ParameterizedTest(name = "Memory usage for calculating Fibonacci({0})")
    @MethodSource("fibonacciInputs")
    @DisplayName("Test Fibonacci memory consumption benchmark")
    void memoryBenchmark(int n) {
        System.gc();
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            String key = task.getKey();
            Function<Integer, Long> func = task.getValue();

            long memory = measureMemoryUsage(() -> func.apply(n));
            memoryUsages.get(key).put(n, memory);
        }
    }

    @ParameterizedTest(name = "Performance for calculating Fibonacci({0})")
    @MethodSource("fibonacciInputs")
    @DisplayName("Test Fibonacci performance benchmark")
    void performanceBenchmark(int n) {
        System.gc();
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            String key = task.getKey();
            Function<Integer, Long> func = task.getValue();

            long time = measureExecutionTime(() -> func.apply(n));
            executionTimes.get(key).put(n, time);
        }

    }


    private static void printResults(Map<String, Map<Integer, Long>> resultMap, String units) {
        for (int n : TEST_INPUTS) {
            Object[] allArgs = new Object[tasks.size() + 1];

            int i = 0;
            allArgs[i] = n;
            for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
                String key = task.getKey();
                allArgs[++i] = resultMap.get(key).get(n) + " " + units;
            }
            System.out.printf("%-5d | %-12s | %-12s | %-12s%n", allArgs);
        }
    }

    private static void printPerformanceResults() {
        printHeader("⏱️ PERFORMANCE BENCHMARK RESULTS");
        printResults(executionTimes, "ms");
    }

    private static void printMemoryUsageResults() {
        printHeader("\n🧠 MEMORY USAGE ANALYSIS");
        printResults(memoryUsages, "KB");
    }

    private static void printHeader(String title) {
        System.out.println(title);

        Object[] argsArray = new Object[tasks.size() + 1];
        int i = 0;
        argsArray[i] = "Input";
        for (Map.Entry<String, Function<Integer, Long>> task : tasks.entrySet()) {
            argsArray[++i] = task.getKey();
        }

        System.out.printf("%-5s | %-12s | %-12s | %-12s%n", argsArray);
        System.out.println("------------------------------------------------");
    }

    private long measureExecutionTime(Runnable task) {

        long startTime = System.currentTimeMillis();
        task.run();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private long measureMemoryUsage(Runnable task) {

        System.gc();

        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        task.run();

        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        return (afterMemory - beforeMemory) / 1024;
    }

}
